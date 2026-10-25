package com.hachi.api.dto;

import java.time.LocalDateTime;

public class FeedbackRespostaDTO {
    private Integer idFeedback;
    private Integer nota;
    private String comentario;
    private LocalDateTime dataHora;
    private Integer idUsuario;

    public FeedbackRespostaDTO() {}

    public FeedbackRespostaDTO(Integer idFeedback, Integer nota, String comentario,
                               LocalDateTime dataHora, Integer idUsuario) {
        this.idFeedback = idFeedback;
        this.nota = nota;
        this.comentario = comentario;
        this.dataHora = dataHora;
        this.idUsuario = idUsuario;
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

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}
