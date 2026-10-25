package com.hachi.api.service;

import com.hachi.api.Entity.Categoria;
import com.hachi.api.dto.CategoriaCadastroDTO;
import com.hachi.api.dto.CategoriaRespostaDTO;
import com.hachi.api.repository.CategoriaRepository;
import com.hachi.api.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepositorio;
    private final ProdutoRepository produtoRepositorio;
    private final AutorizacaoAdminService autorizacaoAdminService;

    public CategoriaService(CategoriaRepository categoriaRepositorio, ProdutoRepository produtoRepositorio,
                            AutorizacaoAdminService autorizacaoAdminService) {
        this.categoriaRepositorio = categoriaRepositorio;
        this.produtoRepositorio = produtoRepositorio;
        this.autorizacaoAdminService = autorizacaoAdminService;
    }

    @Transactional(readOnly = true)
    public List<CategoriaRespostaDTO> listarCategorias() {
        return categoriaRepositorio.findAll().stream().map(this::paraResposta).toList();
    }

    @Transactional(readOnly = true)
    public CategoriaRespostaDTO buscarCategoria(Integer id) {
        return paraResposta(obterCategoria(id));
    }

    @Transactional
    public CategoriaRespostaDTO cadastrarCategoria(CategoriaCadastroDTO dto, Integer idUsuarioAutenticado) {
        autorizacaoAdminService.validarAdministrador(idUsuarioAutenticado);
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome().trim());
        categoria.setDescricao(normalizarOpcional(dto.getDescricao()));
        return paraResposta(categoriaRepositorio.save(categoria));
    }

    @Transactional
    public CategoriaRespostaDTO atualizarCategoria(Integer id, CategoriaCadastroDTO dto,
                                                     Integer idUsuarioAutenticado) {
        autorizacaoAdminService.validarAdministrador(idUsuarioAutenticado);
        Categoria categoria = obterCategoria(id);
        categoria.setNome(dto.getNome().trim());
        categoria.setDescricao(normalizarOpcional(dto.getDescricao()));
        return paraResposta(categoriaRepositorio.save(categoria));
    }

    @Transactional
    public void excluirCategoria(Integer id, Integer idUsuarioAutenticado) {
        autorizacaoAdminService.validarAdministrador(idUsuarioAutenticado);
        Categoria categoria = obterCategoria(id);
        if (produtoRepositorio.existsByCategoria_IdCategoria(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Não é possível excluir uma categoria que possui produtos vinculados.");
        }
        categoriaRepositorio.delete(categoria);
    }

    private Categoria obterCategoria(Integer id) {
        return categoriaRepositorio.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada."));
    }

    private CategoriaRespostaDTO paraResposta(Categoria categoria) {
        return new CategoriaRespostaDTO(categoria.getIdCategoria(), categoria.getNome(), categoria.getDescricao());
    }

    private String normalizarOpcional(String valor) {
        if (valor == null || valor.isBlank()) return null;
        return valor.trim();
    }
}
