package com.hachi.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FeedbackCadastroDTO {

    @NotNull(message = "A nota é obrigatória.")
    @Min(value = 0, message = "A nota deve ser no mínimo 0.")
    @Max(value = 5, message = "A nota deve ser no máximo 5.")
    private Integer nota;

    @NotBlank(message = "O comentário é obrigatório.")
    @Size(max = 255, message = "O comentário deve ter no máximo 255 caracteres.")
    private String comentario;

    public FeedbackCadastroDTO() {}

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
}
