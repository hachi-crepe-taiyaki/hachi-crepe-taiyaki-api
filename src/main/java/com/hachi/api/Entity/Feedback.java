package com.hachi.api.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFeedback")
    private Integer idFeedback;

    @Column(name = "nota")
    private Integer nota;

    @Column(name = "comentario", length = 255)
    private String comentario;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "fk_usuario", nullable = false)
    private Usuario usuario;

    public Feedback(Integer idFeedback, Integer nota, String comentario, LocalDateTime dataHora, Usuario usuario) {
        this.idFeedback = idFeedback;
        this.nota = nota;
        this.comentario = comentario;
        this.dataHora = dataHora;
        this.usuario = usuario;
    }

    public Feedback() {
    }

    public Integer getIdFeedback() {
        return idFeedback;
    }

    public void setIdFeedback(Integer idFeedback) {
        this.idFeedback = idFeedback;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}