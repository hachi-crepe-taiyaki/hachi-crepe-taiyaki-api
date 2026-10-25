package com.hachi.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.hachi.api.dto.ProdutoCadastroDTO;
import com.hachi.api.dto.ProdutoRespostaDTO;
import com.hachi.api.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Tag(name = "Produtos", description = "Consulta e gerenciamento dos produtos disponíveis no cardápio.")
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(summary = "Listar produtos", description = "Retorna todos os produtos cadastrados, incluindo os dados da categoria associada. Não exige autenticação.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Produtos retornados com sucesso.")})
    @GetMapping
    public ResponseEntity<List<ProdutoRespostaDTO>> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarProdutos());
    }

    @Operation(summary = "Buscar produto por ID", description = "Retorna os dados de um produto e de sua categoria. Retorna 404 se o produto não existir.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Produto encontrado."), @ApiResponse(responseCode = "404", description = "Produto não encontrado.")})
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoRespostaDTO> buscarProduto(@Parameter(description = "Identificador numérico do produto.", example = "1") @PathVariable Integer id) {
        return ResponseEntity.ok(produtoService.buscarProduto(id));
    }

    @Operation(summary = "Cadastrar produto", description = "Cria um produto vinculado a uma categoria existente. Requer administrador autenticado. Nome, preço, disponibilidade e categoria são validados; descrição e URL da imagem são opcionais.")
    @SecurityRequirement(name = "cookieAuth")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Produto criado."), @ApiResponse(responseCode = "400", description = "Dados inválidos ou categoria informada inválida."), @ApiResponse(responseCode = "401", description = "Usuário não autenticado."), @ApiResponse(responseCode = "403", description = "Usuário não é administrador.")})
    @PostMapping
    public ResponseEntity<ProdutoRespostaDTO> cadastrarProduto(
            @Valid @RequestBody ProdutoCadastroDTO dto, Authentication autenticacao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                produtoService.cadastrarProduto(dto, obterIdAutenticado(autenticacao)));
    }

    @Operation(summary = "Atualizar produto", description = "Atualiza os dados editáveis do produto identificado, incluindo sua categoria. Requer administrador autenticado.")
    @SecurityRequirement(name = "cookieAuth")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Produto atualizado."), @ApiResponse(responseCode = "400", description = "Dados inválidos ou categoria informada inválida."), @ApiResponse(responseCode = "401", description = "Usuário não autenticado."), @ApiResponse(responseCode = "403", description = "Usuário não é administrador."), @ApiResponse(responseCode = "404", description = "Produto não encontrado.")})
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoRespostaDTO> atualizarProduto(
            @Parameter(description = "Identificador numérico do recurso no caminho da URL.", example = "1") @PathVariable Integer id, @Valid @RequestBody ProdutoCadastroDTO dto,
            Authentication autenticacao) {
        return ResponseEntity.ok(
                produtoService.atualizarProduto(id, dto, obterIdAutenticado(autenticacao)));
    }

    @Operation(summary = "Excluir produto", description = "Remove o produto identificado. Requer administrador autenticado.")
    @SecurityRequirement(name = "cookieAuth")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Produto excluído."), @ApiResponse(responseCode = "401", description = "Usuário não autenticado."), @ApiResponse(responseCode = "403", description = "Usuário não é administrador."), @ApiResponse(responseCode = "404", description = "Produto não encontrado.")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProduto(@Parameter(description = "Identificador numérico do recurso no caminho da URL.", example = "1") @PathVariable Integer id, Authentication autenticacao) {
        produtoService.excluirProduto(id, obterIdAutenticado(autenticacao));
        return ResponseEntity.noContent().build();
    }

    private Integer obterIdAutenticado(Authentication autenticacao) {
        if (autenticacao == null || autenticacao.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "É necessário estar autenticado para gerenciar o cardápio.");
        }
        try {
            return Integer.valueOf(autenticacao.getName());
        } catch (NumberFormatException excecao) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "A autenticação é inválida.");
        }
    }
}
