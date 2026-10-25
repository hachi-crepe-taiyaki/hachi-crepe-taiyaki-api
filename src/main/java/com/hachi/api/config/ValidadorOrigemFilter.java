package com.hachi.api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidadorOrigemFilter extends OncePerRequestFilter {
    private final Set<String> origensPermitidas;

    public ValidadorOrigemFilter(String origens) {
        this.origensPermitidas = Arrays.stream(origens.split(","))
                .map(String::trim).filter(origem -> !origem.isEmpty())
                .collect(Collectors.toUnmodifiableSet());
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