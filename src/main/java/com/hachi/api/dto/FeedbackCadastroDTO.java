package com.hachi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(name = "FeedbackCadastroDTO", description = "Dados enviados pelo usuário autenticado para registrar um feedback.")
@Schema(name = "FeedbackCadastroDTO", description = "Dados enviados pelo usuário autenticado para registrar um feedback.")
public class FeedbackCadastroDTO {

    @NotNull(message = "A nota é obrigatória.")
    @Min(value = 0, message = "A nota deve ser no mínimo 0.")
    @Max(value = 5, message = "A nota deve ser no máximo 5.")
    @Schema(description = "Nota de avaliação de 0 a 5, inclusive. Obrigatória.")
    @Schema(description = "Nota de avaliação de 0 a 5, inclusive. Obrigatória.")
    private Integer nota;

    @NotBlank(message = "O comentário é obrigatório.")
    @Size(max = 255, message = "O comentário deve ter no máximo 255 caracteres.")
    @Schema(description = "Comentário do feedback. Obrigatório; máximo de 255 caracteres.")
    @Schema(description = "Comentário do feedback. Obrigatório; máximo de 255 caracteres.")
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
