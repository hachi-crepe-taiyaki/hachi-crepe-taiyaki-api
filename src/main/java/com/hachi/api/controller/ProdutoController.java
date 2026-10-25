package com.hachi.api.controller;

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

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoRespostaDTO>> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarProdutos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoRespostaDTO> buscarProduto(@PathVariable Integer id) {
        return ResponseEntity.ok(produtoService.buscarProduto(id));
    }

    @PostMapping
    public ResponseEntity<ProdutoRespostaDTO> cadastrarProduto(
            @Valid @RequestBody ProdutoCadastroDTO dto, Authentication autenticacao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                produtoService.cadastrarProduto(dto, obterIdAutenticado(autenticacao)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoRespostaDTO> atualizarProduto(
            @PathVariable Integer id, @Valid @RequestBody ProdutoCadastroDTO dto,
            Authentication autenticacao) {
        return ResponseEntity.ok(
                produtoService.atualizarProduto(id, dto, obterIdAutenticado(autenticacao)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable Integer id, Authentication autenticacao) {
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
