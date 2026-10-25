package com.hachi.api.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class AutenticacaoFilter extends OncePerRequestFilter {
    public static final String COOKIE_NOME = "hachi_access_token";
    private final GerenciadorTokenJwt gerenciadorTokenJwt;

    public AutenticacaoFilter(GerenciadorTokenJwt gerenciadorTokenJwt) {
        this.gerenciadorTokenJwt = gerenciadorTokenJwt;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest requisicao, HttpServletResponse resposta,
                                    FilterChain cadeia) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String token = extrairToken(requisicao.getCookies());
            if (token != null && gerenciadorTokenJwt.validarToken(token)) {
                Claims dados = gerenciadorTokenJwt.obterDadosToken(token);
                Integer idUsuario = Integer.valueOf(dados.getSubject());
                Object funcaoToken = dados.get("funcao");
                String autoridade = "ROLE_CLIENT";
                if (funcaoToken != null) {
                    int funcao = Integer.parseInt(funcaoToken.toString());
                    if (funcao == 1) autoridade = "ROLE_EMPLOYEE";
                    else if (funcao == 2) autoridade = "ROLE_ADMIN";
                }
                UsernamePasswordAuthenticationToken autenticacao =
                        new UsernamePasswordAuthenticationToken(idUsuario.toString(), null,
                                List.of(new SimpleGrantedAuthority(autoridade)));
                SecurityContextHolder.getContext().setAuthentication(autenticacao);
            }
        }
        cadeia.doFilter(requisicao, resposta);
    }

    private String extrairToken(Cookie[] cookies) {
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (COOKIE_NOME.equals(cookie.getName())) return cookie.getValue();
        }
        return null;
    }
}