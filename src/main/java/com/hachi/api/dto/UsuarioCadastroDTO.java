package com.hachi.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record UsuarioCadastroDTO(
        @NotBlank @Size(max = 100) String nome,
        @NotBlank @Email @Size(max = 100) String email,
        @NotBlank @Size(min = 8, max = 72) String senha,
        @Size(max = 11) String telefone,
        @Past LocalDate dtNasc,
        @Size(max = 100) String cidade,
        @Size(max = 100) String regiao
) {}