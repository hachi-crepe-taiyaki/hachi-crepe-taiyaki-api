package com.hachi.api.dto;

import java.math.BigDecimal;

public class CodigoRespostaDTO {
    private Integer idCodigos;
    private String codigo;
    private Integer statusScan;
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
