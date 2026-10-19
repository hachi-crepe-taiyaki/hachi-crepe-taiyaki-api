package com.hachi.api.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProduto")
    private Integer idProduto;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "descricao", length = 255)
    private String descricao;

    @Column(name = "preco", nullable = false, precision = 5, scale = 2)
    private BigDecimal preco;

    @Column(name = "imagem_url", length = 255)
    private String imagemUrl;

    @Column(name = "disponivel")
    private Byte disponivel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_categoria", nullable = false)
    private Categoria categoria;

    public Produto(Integer idProduto, String nome, String descricao, BigDecimal preco, String imagemUrl, Byte disponivel, Categoria categoria) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.imagemUrl = imagemUrl;
        this.disponivel = disponivel;
        this.categoria = categoria;
    }

    public Produto() {
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public Byte getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Byte disponivel) {
        this.disponivel = disponivel;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

}