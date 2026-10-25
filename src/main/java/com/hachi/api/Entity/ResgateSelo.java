package com.hachi.api.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resgate_selo")
public class ResgateSelo {

    @Id
    @ManyToOne
    @JoinColumn(name = "codigos_idCodigos")
    private Codigo codigo;

    @Id
    @ManyToOne
    @JoinColumn(name = "usuario_idUsuario")
    private Usuario usuario;

    @Id
    @Column(name = "idResgate")
    private Integer idResgate;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    public ResgateSelo() {
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

    public Integer getIdResgate() {
        return idResgate;
    }

    public void setIdResgate(Integer idResgate) {
        this.idResgate = idResgate;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}