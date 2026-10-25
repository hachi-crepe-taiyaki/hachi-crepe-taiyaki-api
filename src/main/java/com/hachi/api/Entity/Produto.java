package com.hachi.api.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProduto")
    private Integer idProduto;

    @Column(length = 100)
    private String nome;

    @Column(length = 255)
    private String descricao;

    @Column(precision = 5, scale = 2)
    private BigDecimal preco;

    @Column(name = "imagem_url", length = 255)
    private String imagemUrl;

    private Integer disponivel;

    @ManyToOne
    @JoinColumn(name = "fk_categoria")
    private Categoria categoria;

    public Produto() {}

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

    public Integer getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Integer disponivel) {
        this.disponivel = disponivel;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}