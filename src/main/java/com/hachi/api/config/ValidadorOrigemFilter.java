package com.hachi.api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ValidadorOrigemFilter extends OncePerRequestFilter {
    private final Set<String> origensPermitidas = new HashSet<>();

    public ValidadorOrigemFilter(String origens) {
        for (String origem : Arrays.asList(origens.split(","))) {
            String origemLimpa = origem.trim();
            if (!origemLimpa.isEmpty()) {
                this.origensPermitidas.add(origemLimpa);
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest requisicao, HttpServletResponse resposta,
                                    FilterChain cadeia) throws ServletException, IOException {
        String metodo = requisicao.getMethod();
        boolean alteraEstado = !("GET".equals(metodo) || "HEAD".equals(metodo)
                || "OPTIONS".equals(metodo) || "TRACE".equals(metodo));
        String origem = requisicao.getHeader("Origin");
        if (alteraEstado && origem != null && !origensPermitidas.contains(origem)) {
            resposta.sendError(HttpServletResponse.SC_FORBIDDEN, "A origem desta requisição não é permitida.");
            return;
        }
        cadeia.doFilter(requisicao, resposta);
    }
}