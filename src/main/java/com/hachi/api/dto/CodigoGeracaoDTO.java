package com.hachi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(name = "CodigoGeracaoDTO", description = "Dados enviados para gerar um código associado ao valor de uma compra.")
public class CodigoGeracaoDTO {

    @NotNull(message = "O valor gasto é obrigatório.")
    @DecimalMin(value = "40.00", message = "O valor gasto deve ser de pelo menos R$ 40,00.")
    @Digits(integer = 3, fraction = 2, message = "O valor deve ter no máximo 3 dígitos inteiros e 2 casas decimais.")
    @Schema(description = "Valor da compra em reais. Obrigatório, mínimo de R$ 40,00 e até duas casas decimais.")
    private BigDecimal valor;

    public CodigoGeracaoDTO() {}

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
