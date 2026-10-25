package com.hachi.api.dto;

import java.math.BigDecimal;

public class ProdutoRespostaDTO {
    private Integer idProduto;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String imagemUrl;
    private Integer disponivel;
    private Integer idCategoria;
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
