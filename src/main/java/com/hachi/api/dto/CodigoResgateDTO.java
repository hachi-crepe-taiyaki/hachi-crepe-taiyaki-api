package com.hachi.api.DTO;

import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.validation.constraints.NotBlank;

@Schema(name = "CodigoResgateDTO", description = "Dados necessários para resgatar um código e receber um selo.")
public class CodigoResgateDTO {
    @NotBlank(message = "O código do selo é obrigatório.")
    @Schema(description = "Código de compra recebido no estabelecimento. Obrigatório.")
    private String codigo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
