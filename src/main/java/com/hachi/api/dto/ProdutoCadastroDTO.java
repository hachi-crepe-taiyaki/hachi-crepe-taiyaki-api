package com.hachi.api.DTO;

import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(name = "ProdutoCadastroDTO", description = "Dados necessários para cadastrar ou atualizar um produto do cardápio.")
public class ProdutoCadastroDTO {
    @NotBlank(message = "O nome do produto é obrigatório.")
    @Size(max = 100, message = "O nome do produto deve ter no máximo 100 caracteres.")
    @Schema(description = "Nome do produto. Obrigatório; máximo de 100 caracteres.")
    private String nome;

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
    @Schema(description = "Descrição opcional do produto; máximo de 255 caracteres.")
    private String descricao;

    @NotNull(message = "O preço do produto é obrigatório.")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
    @Digits(integer = 3, fraction = 2, message = "O preço deve ter no máximo 3 dígitos inteiros e 2 decimais.")
    @Schema(description = "Preço em reais, maior que zero, com até duas casas decimais.")
    private BigDecimal preco;

    @Size(max = 255, message = "A URL da imagem deve ter no máximo 255 caracteres.")
    @Schema(description = "URL opcional da imagem do produto; máximo de 255 caracteres.")
    private String imagemUrl;

    @NotNull(message = "A disponibilidade é obrigatória.")
    @Min(value = 0, message = "A disponibilidade deve ser 0 ou 1.")
    @Max(value = 1, message = "A disponibilidade deve ser 0 ou 1.")
    @Schema(description = "Indica disponibilidade: 1 para disponível e 0 para indisponível.")
    private Integer disponivel;

    @NotNull(message = "A categoria do produto é obrigatória.")
    @Min(value = 1, message = "O ID da categoria deve ser positivo.")
    @Schema(description = "ID de uma categoria existente à qual o produto será vinculado.")
    private Integer idCategoria;

    public ProdutoCadastroDTO() {}

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
}
