package com.hachi.api.dto;

import jakarta.validation.constraints.NotBlank;

public class CodigoResgateDTO {
    @NotBlank(message = "O código do selo é obrigatório.")
    private String codigo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
