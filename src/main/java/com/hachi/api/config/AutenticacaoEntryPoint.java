package com.hachi.api.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AutenticacaoEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest requisicao, HttpServletResponse resposta,
                         AuthenticationException excecao) throws IOException, ServletException {
        resposta.sendError(HttpServletResponse.SC_UNAUTHORIZED, "É necessário estar autenticado para acessar este recurso.");
    }
}