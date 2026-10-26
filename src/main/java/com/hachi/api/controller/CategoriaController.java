package com.hachi.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.hachi.api.DTO.CategoriaCadastroDTO;
import com.hachi.api.DTO.CategoriaRespostaDTO;
import com.hachi.api.Service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Tag(name = "Categorias", description = "Consulta e gerenciamento das categorias do cardápio. Consultas são públicas; alterações exigem um administrador autenticado.")
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Operation(summary = "Listar categorias", description = "Retorna todas as categorias cadastradas no cardápio. Não exige autenticação.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Categorias retornadas com sucesso.")})
    @GetMapping
    public ResponseEntity<List<CategoriaRespostaDTO>> listarCategorias() {
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    @Operation(summary = "Buscar categoria por ID", description = "Retorna os dados de uma categoria específica. Retorna 404 se o ID não existir.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Categoria encontrada."), @ApiResponse(responseCode = "404", description = "Categoria não encontrada.")})
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaRespostaDTO> buscarCategoria(@Parameter(description = "Identificador numérico da categoria.", example = "1") @PathVariable Integer id) {
        return ResponseEntity.ok(categoriaService.buscarCategoria(id));
    }

    @Operation(summary = "Cadastrar categoria", description = "Cria uma categoria para o cardápio. Requer autenticação por cookie JWT e permissão de administrador. O nome é obrigatório e os campos são validados.")
    @SecurityRequirement(name = "cookieAuth")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Categoria criada."), @ApiResponse(responseCode = "400", description = "Dados inválidos."), @ApiResponse(responseCode = "401", description = "Usuário não autenticado."), @ApiResponse(responseCode = "403", description = "Usuário não é administrador.")})
    @PostMapping
    public ResponseEntity<CategoriaRespostaDTO> cadastrarCategoria(
            @Valid @RequestBody CategoriaCadastroDTO dto, Authentication autenticacao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                categoriaService.cadastrarCategoria(dto, obterIdAutenticado(autenticacao)));
    }

    @Operation(summary = "Atualizar categoria", description = "Substitui os dados editáveis da categoria identificada. Requer administrador autenticado. Retorna 404 se a categoria não existir e 400 para dados inválidos.")
    @SecurityRequirement(name = "cookieAuth")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Categoria atualizada."), @ApiResponse(responseCode = "400", description = "Dados inválidos."), @ApiResponse(responseCode = "401", description = "Usuário não autenticado."), @ApiResponse(responseCode = "403", description = "Usuário não é administrador."), @ApiResponse(responseCode = "404", description = "Categoria não encontrada.")})
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaRespostaDTO> atualizarCategoria(
            @Parameter(description = "Identificador numérico do recurso no caminho da URL.", example = "1") @PathVariable Integer id, @Valid @RequestBody CategoriaCadastroDTO dto,
            Authentication autenticacao) {
        return ResponseEntity.ok(
                categoriaService.atualizarCategoria(id, dto, obterIdAutenticado(autenticacao)));
    }

    @Operation(summary = "Excluir categoria", description = "Remove uma categoria sem produtos associados. Requer administrador autenticado. A categoria não pode ser excluída enquanto possuir produtos.")
    @SecurityRequirement(name = "cookieAuth")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Categoria excluída."), @ApiResponse(responseCode = "401", description = "Usuário não autenticado."), @ApiResponse(responseCode = "403", description = "Usuário não é administrador."), @ApiResponse(responseCode = "404", description = "Categoria não encontrada."), @ApiResponse(responseCode = "409", description = "Categoria possui produtos associados e não pode ser excluída.")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCategoria(@Parameter(description = "Identificador numérico do recurso no caminho da URL.", example = "1") @PathVariable Integer id, Authentication autenticacao) {
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
