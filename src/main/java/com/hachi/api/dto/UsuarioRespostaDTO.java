package com.hachi.api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UsuarioRespostaDTO(
        Integer idUsuario,
        String nome,
        String email,
        String telefone,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm,
        Integer statusUsuario,
        Integer funcao,
        LocalDate dtNasc,
        String cidade,
        String regiao,
        Integer selos
) {}