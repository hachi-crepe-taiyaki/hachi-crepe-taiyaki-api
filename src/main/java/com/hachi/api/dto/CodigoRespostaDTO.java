package com.hachi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;


import java.math.BigDecimal;

@Schema(name = "CodigoRespostaDTO", description = "Dados do código de fidelidade gerado.")
public class CodigoRespostaDTO {
    @Schema(description = "Identificador do registro do código.")
    private Integer idCodigos;
    @Schema(description = "Código alfanumérico que o cliente apresenta para resgatar um selo.")
    private String codigo;
    @Schema(description = "Estado de utilização do código conforme o sistema de fidelidade.")
    private Integer statusScan;
    @Schema(description = "Valor da compra associado ao código, em reais.")
    private BigDecimal valor;

    public CodigoRespostaDTO() {}

    public CodigoRespostaDTO(Integer idCodigos, String codigo, Integer statusScan, BigDecimal valor) {
        this.idCodigos = idCodigos;
        this.codigo = codigo;
        this.statusScan = statusScan;
        this.valor = valor;
    }

    public Integer getIdCodigos() {
        return idCodigos;
    }

    public void setIdCodigos(Integer idCodigos) {
        this.idCodigos = idCodigos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getStatusScan() {
        return statusScan;
    }

    public void setStatusScan(Integer statusScan) {
        this.statusScan = statusScan;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
