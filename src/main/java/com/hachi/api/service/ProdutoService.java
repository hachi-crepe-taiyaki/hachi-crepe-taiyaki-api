package com.hachi.api.service;

import com.hachi.api.Entity.Categoria;
import com.hachi.api.Entity.Produto;
import com.hachi.api.dto.ProdutoCadastroDTO;
import com.hachi.api.dto.ProdutoRespostaDTO;
import com.hachi.api.repository.CategoriaRepository;
import com.hachi.api.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepositorio;
    private final CategoriaRepository categoriaRepositorio;
    private final AutorizacaoAdminService autorizacaoAdminService;

    public ProdutoService(ProdutoRepository produtoRepositorio, CategoriaRepository categoriaRepositorio,
                          AutorizacaoAdminService autorizacaoAdminService) {
        this.produtoRepositorio = produtoRepositorio;
        this.categoriaRepositorio = categoriaRepositorio;
        this.autorizacaoAdminService = autorizacaoAdminService;
    }

    @Transactional(readOnly = true)
    public List<ProdutoRespostaDTO> listarProdutos() {
        return produtoRepositorio.findAllByOrderByNomeAsc().stream().map(this::paraResposta).toList();
    }

    @Transactional(readOnly = true)
    public ProdutoRespostaDTO buscarProduto(Integer id) {
        return paraResposta(obterProduto(id));
    }

    @Transactional
    public ProdutoRespostaDTO cadastrarProduto(ProdutoCadastroDTO dto, Integer idUsuarioAutenticado) {
        autorizacaoAdminService.validarAdministrador(idUsuarioAutenticado);
        Produto produto = new Produto();
        aplicarDados(produto, dto);
        return paraResposta(produtoRepositorio.save(produto));
    }

    @Transactional
    public ProdutoRespostaDTO atualizarProduto(Integer id, ProdutoCadastroDTO dto, Integer idUsuarioAutenticado) {
        autorizacaoAdminService.validarAdministrador(idUsuarioAutenticado);
        Produto produto = obterProduto(id);
        aplicarDados(produto, dto);
        return paraResposta(produtoRepositorio.save(produto));
    }

    @Transactional
    public void excluirProduto(Integer id, Integer idUsuarioAutenticado) {
        autorizacaoAdminService.validarAdministrador(idUsuarioAutenticado);
        produtoRepositorio.delete(obterProduto(id));
    }

    private void aplicarDados(Produto produto, ProdutoCadastroDTO dto) {
        Categoria categoria = categoriaRepositorio.findById(dto.getIdCategoria()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada."));

        produto.setNome(dto.getNome().trim());
        produto.setDescricao(normalizarOpcional(dto.getDescricao()));
        produto.setPreco(dto.getPreco());
        produto.setImagemUrl(normalizarOpcional(dto.getImagemUrl()));
        produto.setDisponivel(dto.getDisponivel());
        produto.setCategoria(categoria);
    }

    private Produto obterProduto(Integer id) {
        return produtoRepositorio.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));
    }

    private ProdutoRespostaDTO paraResposta(Produto produto) {
        Categoria categoria = produto.getCategoria();
        return new ProdutoRespostaDTO(
                produto.getIdProduto(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getImagemUrl(),
                produto.getDisponivel(),
                categoria == null ? null : categoria.getIdCategoria(),
                categoria == null ? null : categoria.getNome()
        );
    }

    private String normalizarOpcional(String valor) {
        if (valor == null || valor.isBlank()) return null;
        return valor.trim();
    }
}
