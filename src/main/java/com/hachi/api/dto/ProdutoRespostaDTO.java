package com.hachi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(name = "ProdutoRespostaDTO", description = "Representação de um produto do cardápio, incluindo sua categoria.")
@Schema(name = "ProdutoRespostaDTO", description = "Representação de um produto do cardápio, incluindo sua categoria.")
public class ProdutoRespostaDTO {
    @Schema(description = "Identificador único do produto.")
    @Schema(description = "Identificador único do produto.")
    private Integer idProduto;
    @Schema(description = "Nome do produto.")
    @Schema(description = "Nome do produto.")
    private String nome;
    @Schema(description = "Descrição do produto.")
    @Schema(description = "Descrição do produto.")
    private String descricao;
    @Schema(description = "Preço do produto em reais.")
    @Schema(description = "Preço do produto em reais.")
    private BigDecimal preco;
    @Schema(description = "URL da imagem do produto, quando cadastrada.")
    @Schema(description = "URL da imagem do produto, quando cadastrada.")
    private String imagemUrl;
    @Schema(description = "Disponibilidade: 1 para disponível e 0 para indisponível.")
    @Schema(description = "Disponibilidade: 1 para disponível e 0 para indisponível.")
    private Integer disponivel;
    @Schema(description = "Identificador da categoria associada.")
    @Schema(description = "Identificador da categoria associada.")
    private Integer idCategoria;
    @Schema(description = "Nome da categoria associada.")
    @Schema(description = "Nome da categoria associada.")
    private String nomeCategoria;

    public ProdutoRespostaDTO() {}

    public ProdutoRespostaDTO(Integer idProduto, String nome, String descricao, BigDecimal preco,
                              String imagemUrl, Integer disponivel, Integer idCategoria, String nomeCategoria) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.imagemUrl = imagemUrl;
        this.disponivel = disponivel;
        this.idCategoria = idCategoria;
        this.nomeCategoria = nomeCategoria;
    }

    public Integer getIdProduto() { return idProduto; }
    public void setIdProduto(Integer idProduto) { this.idProduto = idProduto; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }

    public Integer getDisponivel() { return disponivel; }
    public void setDisponivel(Integer disponivel) { this.disponivel = disponivel; }

    public Integer getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Integer idCategoria) { this.idCategoria = idCategoria; }

    public String getNomeCategoria() { return nomeCategoria; }
    public void setNomeCategoria(String nomeCategoria) { this.nomeCategoria = nomeCategoria; }
}
