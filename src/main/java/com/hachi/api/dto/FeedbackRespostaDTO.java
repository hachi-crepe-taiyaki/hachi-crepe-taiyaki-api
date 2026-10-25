package com.hachi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;


import java.time.LocalDateTime;

@Schema(name = "FeedbackRespostaDTO", description = "Representação de um feedback já registrado.")
public class FeedbackRespostaDTO {
    @Schema(description = "Identificador único do feedback.")
    private Integer idFeedback;
    @Schema(description = "Nota de avaliação de 0 a 5.")
    private Integer nota;
    @Schema(description = "Comentário enviado pelo usuário.")
    private String comentario;
    @Schema(description = "Data e hora em que o feedback foi registrado.")
    private LocalDateTime dataHora;
    @Schema(description = "Identificador do usuário que enviou o feedback.")
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
