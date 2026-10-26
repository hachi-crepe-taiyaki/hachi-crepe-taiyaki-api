package com.hachi.api.DTO;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "CategoriaRespostaDTO", description = "Representação de uma categoria retornada pela API.")
public class CategoriaRespostaDTO {
    @Schema(description = "Identificador único da categoria.")
    private Integer idCategoria;
    @Schema(description = "Nome da categoria.")
    private String nome;
    @Schema(description = "Descrição da categoria.")
    private String descricao;

    public CategoriaRespostaDTO() {}

    public CategoriaRespostaDTO(Integer idCategoria, String nome, String descricao) {
        this.idCategoria = idCategoria;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Integer getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Integer idCategoria) { this.idCategoria = idCategoria; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
