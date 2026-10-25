package com.hachi.api.config;

import com.hachi.api.Entity.Categoria;
import com.hachi.api.Entity.Produto;
import com.hachi.api.Entity.Usuario;
import com.hachi.api.repository.CategoriaRepository;
import com.hachi.api.repository.ProdutoRepository;
import com.hachi.api.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Profile("!prod")
public class DadosTesteInicializador implements CommandLineRunner {
    private final UsuarioRepository usuarios;
    private final CategoriaRepository categorias;
    private final ProdutoRepository produtos;
    private final PasswordEncoder codificadorSenha;

    public DadosTesteInicializador(UsuarioRepository usuarios, CategoriaRepository categorias,
                                   ProdutoRepository produtos, PasswordEncoder codificadorSenha) {
        this.usuarios = usuarios;
        this.categorias = categorias;
        this.produtos = produtos;
        this.codificadorSenha = codificadorSenha;
    }

    @Override
    public void run(String... args) {
        criarUsuario("Marley", "marley@hachi.test", "Marley123!", 0, "11987654321", LocalDate.of(2000, 5, 15));
        criarUsuario("Julia", "julia@hachi.test", "Julia123!", 1, "11987654322", LocalDate.of(1998, 8, 22));
        criarUsuario("Leonardo", "leonardo@hachi.test", "Leonardo123!", 2, "11987654323", LocalDate.of(1995, 3, 10));

        Categoria cafes = criarCategoria("Cafés", "Cafés quentes e gelados preparados na hora.");
        Categoria doces = criarCategoria("Doces", "Sobremesas e doces para acompanhar seu café.");
        Categoria salgados = criarCategoria("Salgados", "Lanches salgados preparados diariamente.");

        criarProduto("Espresso", "Café espresso tradicional.", "6.00", cafes);
        criarProduto("Cappuccino", "Café com leite vaporizado e espuma.", "10.00", cafes);
        criarProduto("Café Gelado", "Café gelado levemente adocicado.", "12.00", cafes);

        criarProduto("Brownie", "Brownie de chocolate.", "9.00", doces);
        criarProduto("Cheesecake", "Fatia de cheesecake cremosa.", "14.00", doces);
        criarProduto("Cookie", "Cookie com gotas de chocolate.", "7.00", doces);

        criarProduto("Pão de Queijo", "Pão de queijo assado na hora.", "6.50", salgados);
        criarProduto("Coxinha", "Coxinha de frango.", "8.00", salgados);
        criarProduto("Misto Quente", "Sanduíche de queijo e presunto.", "11.00", salgados);
    }

    private void criarUsuario(String nome, String email, String senha, int funcao,
                              String telefone, LocalDate nascimento) {
        if (usuarios.findByEmailIgnoreCase(email) != null) return;

        LocalDateTime agora = LocalDateTime.now();
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(codificadorSenha.encode(senha));
        usuario.setTelefone(telefone);
        usuario.setCriadoEm(agora);
        usuario.setAtualizadoEm(agora);
        usuario.setStatusUsuario(1);
        usuario.setFuncao(funcao);
        usuario.setDtNasc(nascimento);
        usuario.setCidade("São Paulo");
        usuario.setRegiao("Centro");
        usuario.setSelos(0);
        usuarios.save(usuario);
    }

    private Categoria criarCategoria(String nome, String descricao) {
        for (Categoria categoria : categorias.findAll()) {
            if (categoria.getNome() != null && categoria.getNome().equalsIgnoreCase(nome)) return categoria;
        }
        Categoria categoria = new Categoria();
        categoria.setNome(nome);
        categoria.setDescricao(descricao);
        return categorias.save(categoria);
    }

    private void criarProduto(String nome, String descricao, String preco, Categoria categoria) {
        for (Produto produto : produtos.findAll()) {
            if (produto.getNome() != null && produto.getNome().equalsIgnoreCase(nome)) return;
        }
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(new BigDecimal(preco));
        produto.setImagemUrl(null);
        produto.setDisponivel(1);
        produto.setCategoria(categoria);
        produtos.save(produto);
    }
}
