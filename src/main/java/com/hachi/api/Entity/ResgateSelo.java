package com.hachi.api.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class ResgateSelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idResgateSelo")
    private Integer idResgateSelo;

    @ManyToOne
    @MapsId("codigoId")
    @JoinColumn(name = "codigo_idCodigo")
    private Codigo codigo;

    @ManyToOne
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_idUsuario")
    private Usuario usuario;

    @Column(name = "valor", precision = 5, scale = 2)
    private BigDecimal valor;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    public ResgateSelo(Integer idResgateSelo, Codigo codigo, Usuario usuario, BigDecimal valor, LocalDateTime dataHora) {
        this.idResgateSelo = idResgateSelo;
        this.codigo = codigo;
        this.usuario = usuario;
        this.valor = valor;
        this.dataHora = dataHora;
    }

    public ResgateSelo() {
    }

    public Integer getIdResgateSelo() {
        return idResgateSelo;
    }

    public void setIdResgateSelo(Integer idResgateSelo) {
        this.idResgateSelo = idResgateSelo;
    }

    public Codigo getCodigo() {
        return codigo;
    }

    public void setCodigo(Codigo codigo) {
        this.codigo = codigo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

}