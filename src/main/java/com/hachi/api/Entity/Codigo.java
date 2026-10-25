package com.hachi.api.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Codigo")
public class Codigo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCodigos")
    private Integer idCodigos;

    @Column(length = 6)
    private String codigo;

    private Integer statusScan;

    @Column(precision = 5, scale = 2)
    private BigDecimal valor;

    public Codigo() {}

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