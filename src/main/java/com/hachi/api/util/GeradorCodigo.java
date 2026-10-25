package com.hachi.api.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class GeradorCodigo {
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int TAMANHO_CODIGO = 6;
    private final SecureRandom aleatorio = new SecureRandom();

    public String gerarCodigo() {
        StringBuilder codigo = new StringBuilder(TAMANHO_CODIGO);

        for (int i = 0; i < TAMANHO_CODIGO; i++) {
            int indice = aleatorio.nextInt(CARACTERES.length());
            codigo.append(CARACTERES.charAt(indice));
        }

        return codigo.toString();
    }
}
