package com.hachi.api.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ResgateItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idResgate")
    private Integer idResgate;

    @Column(name = "data_resgate")
    private LocalDateTime dataResgate;

    @ManyToOne
    @JoinColumn(name = "usuario_idUsuario", nullable = false)
    private Usuario usuario;

    public ResgateItem(Integer idResgate, LocalDateTime dataResgate, Usuario usuario) {
        this.idResgate = idResgate;
        this.dataResgate = dataResgate;
        this.usuario = usuario;
    }

    public ResgateItem() {
    }

    public Integer getIdResgate() {
        return idResgate;
    }

    public void setIdResgate(Integer idResgate) {
        this.idResgate = idResgate;
    }

    public LocalDateTime getDataResgate() {
        return dataResgate;
    }

    public void setDataResgate(LocalDateTime dataResgate) {
        this.dataResgate = dataResgate;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
