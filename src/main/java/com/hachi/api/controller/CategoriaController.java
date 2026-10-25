package com.hachi.api.controller;

import com.hachi.api.dto.CategoriaCadastroDTO;
import com.hachi.api.dto.CategoriaRespostaDTO;
import com.hachi.api.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaRespostaDTO>> listarCategorias() {
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaRespostaDTO> buscarCategoria(@PathVariable Integer id) {
        return ResponseEntity.ok(categoriaService.buscarCategoria(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaRespostaDTO> cadastrarCategoria(
            @Valid @RequestBody CategoriaCadastroDTO dto, Authentication autenticacao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                categoriaService.cadastrarCategoria(dto, obterIdAutenticado(autenticacao)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaRespostaDTO> atualizarCategoria(
            @PathVariable Integer id, @Valid @RequestBody CategoriaCadastroDTO dto,
            Authentication autenticacao) {
        return ResponseEntity.ok(
                categoriaService.atualizarCategoria(id, dto, obterIdAutenticado(autenticacao)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCategoria(@PathVariable Integer id, Authentication autenticacao) {
        categoriaService.excluirCategoria(id, obterIdAutenticado(autenticacao));
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
