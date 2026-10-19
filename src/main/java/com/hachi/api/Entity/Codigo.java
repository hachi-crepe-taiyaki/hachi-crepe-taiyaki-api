package com.hachi.api.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

public class Codigo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCodigo")
    private Integer idCodigo;

    @Column(name = "codigo", length = 6, nullable = false)
    private String codigoValor;

    @Column(name = "statusScan")
    private Boolean statusScan;

    public Codigo(Integer idCodigo, String codigoValor, Boolean statusScan) {
        this.idCodigo = idCodigo;
        this.codigoValor = codigoValor;
        this.statusScan = statusScan;
    }

    public Codigo() {
    }

    public Integer getIdCodigo() {
        return idCodigo;
    }

    public void setIdCodigo(Integer idCodigo) {
        this.idCodigo = idCodigo;
    }

    public String getCodigoValor() {
        return codigoValor;
    }

    public void setCodigoValor(String codigoValor) {
        this.codigoValor = codigoValor;
    }

    public Boolean getStatusScan() {
        return statusScan;
    }

    public void setStatusScan(Boolean statusScan) {
        this.statusScan = statusScan;
    }
}
