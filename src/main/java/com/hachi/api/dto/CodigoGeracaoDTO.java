package com.hachi.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CodigoGeracaoDTO {

    @NotNull(message = "O valor gasto é obrigatório.")
    @DecimalMin(value = "40.00", message = "O valor gasto deve ser de pelo menos R$ 40,00.")
    @Digits(integer = 3, fraction = 2, message = "O valor deve ter no máximo 3 dígitos inteiros e 2 casas decimais.")
    private BigDecimal valor;

    public CodigoGeracaoDTO() {}

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
