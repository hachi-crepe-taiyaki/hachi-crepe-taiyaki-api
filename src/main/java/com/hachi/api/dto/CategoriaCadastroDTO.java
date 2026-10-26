package com.hachi.api.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "CategoriaCadastroDTO", description = "Dados necessários para cadastrar ou atualizar uma categoria do cardápio.")
public class CategoriaCadastroDTO {
    @NotBlank(message = "O nome da categoria é obrigatório.")
    @Size(max = 45, message = "O nome da categoria deve ter no máximo 45 caracteres.")
    @Schema(description = "Nome da categoria. Obrigatório; máximo de 45 caracteres.")
    private String nome;

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
    @Schema(description = "Descrição opcional da categoria; máximo de 255 caracteres.")
    private String descricao;

    public CategoriaCadastroDTO() {}

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
