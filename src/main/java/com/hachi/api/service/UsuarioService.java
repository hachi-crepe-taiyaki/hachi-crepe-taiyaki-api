package com.hachi.api.Service;

import com.hachi.api.Entity.Usuario;
import com.hachi.api.config.GerenciadorTokenJwt;
import com.hachi.api.DTO.LoginDTO;
import com.hachi.api.DTO.ResultadoLoginDTO;
import com.hachi.api.DTO.UsuarioAtualizacaoDTO;
import com.hachi.api.DTO.UsuarioCadastroDTO;
import com.hachi.api.DTO.UsuarioRespostaDTO;
import com.hachi.api.Mapper.UsuarioMapper;
import com.hachi.api.Repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository repositorio;
    private final PasswordEncoder codificadorSenha;
    private final UsuarioMapper mapeador;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;

    public UsuarioService(UsuarioRepository repositorio, PasswordEncoder codificadorSenha,
                          UsuarioMapper mapeador, GerenciadorTokenJwt gerenciadorTokenJwt) {
        this.repositorio = repositorio;
        this.codificadorSenha = codificadorSenha;
        this.mapeador = mapeador;
        this.gerenciadorTokenJwt = gerenciadorTokenJwt;
    }

    @Transactional
    public UsuarioRespostaDTO cadastrarUsuario(UsuarioCadastroDTO dto) {
        if (repositorio.countByEmailIgnoreCase(dto.getEmail().trim()) > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este e-mail já está cadastrado.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome().trim());
        usuario.setEmail(dto.getEmail().trim().toLowerCase());
        usuario.setSenha(codificadorSenha.encode(dto.getSenha()));
        usuario.setTelefone(dto.getTelefone());
        usuario.setDtNasc(dto.getDtNasc());
        usuario.setCidade(dto.getCidade());
        usuario.setRegiao(dto.getRegiao());
        usuario.setFuncao(0);
        usuario.setStatusUsuario(1);
        usuario.setSelos(0);
        usuario.setCriadoEm(LocalDateTime.now());
        usuario.setAtualizadoEm(LocalDateTime.now());

        return mapeador.paraResposta(repositorio.save(usuario));
    }

    public ResultadoLoginDTO autenticarUsuario(LoginDTO dto) {
        Usuario usuario = repositorio.findByEmailIgnoreCase(dto.getEmail().trim());
        if (usuario == null || !codificadorSenha.matches(dto.getSenha(), usuario.getSenha())
                || (usuario.getStatusUsuario() != null && usuario.getStatusUsuario() != 1)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "E-mail ou senha inválidos.");
        }

        String token = gerenciadorTokenJwt.gerarToken(
                usuario.getIdUsuario(), usuario.getEmail(), usuario.getFuncao());
        return new ResultadoLoginDTO(token, mapeador.paraResposta(usuario));
    }

    @Transactional(readOnly = true)
    public List<UsuarioRespostaDTO> listarUsuarios(String email, String telefone, LocalDate dtNasc,
                                                   String cidade, String regiao, String nome) {
        List<Usuario> usuarios = repositorio.buscarComFiltros(
                normalizarFiltro(email),
                normalizarFiltro(telefone),
                dtNasc,
                normalizarFiltro(cidade),
                normalizarFiltro(regiao),
                normalizarFiltro(nome)
        );
        return usuarios.stream().map(mapeador::paraResposta).toList();
    }

    private String normalizarFiltro(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }
        return valor.trim();
    }

    @Transactional
    public UsuarioRespostaDTO atualizarUsuario(Integer id, UsuarioAtualizacaoDTO dto,
                                                Integer idUsuarioAutenticado, boolean administrador) {
        if (!administrador && !id.equals(idUsuarioAutenticado)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Você só pode alterar os dados da sua própria conta.");
        }

        Usuario usuario = repositorio.findByIdUsuario(id);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.");
        }
        if (dto.getNome() != null && dto.getNome().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O nome não pode ficar vazio.");
        }
        if (dto.getEmail() != null && dto.getEmail().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O e-mail não pode ficar vazio.");
        }
        if (dto.getEmail() != null && repositorio.countByEmailIgnoreCaseAndIdUsuarioNot(dto.getEmail().trim(), id) > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este e-mail já está cadastrado.");
        }

        if (dto.getNome() != null) usuario.setNome(dto.getNome().trim());
        if (dto.getEmail() != null) usuario.setEmail(dto.getEmail().trim().toLowerCase());
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenha(codificadorSenha.encode(dto.getSenha()));
        }
        if (dto.getTelefone() != null) usuario.setTelefone(dto.getTelefone());
        if (dto.getDtNasc() != null) usuario.setDtNasc(dto.getDtNasc());
        if (dto.getCidade() != null) usuario.setCidade(dto.getCidade());
        if (dto.getRegiao() != null) usuario.setRegiao(dto.getRegiao());

        // A função, o status e a quantidade de selos não podem ser alterados por este endpoint.
        usuario.setAtualizadoEm(LocalDateTime.now());
        return mapeador.paraResposta(repositorio.save(usuario));
    }

    @Transactional
    public void excluirUsuario(Integer id, Integer idUsuarioAutenticado, boolean administrador) {
        if (!administrador && !id.equals(idUsuarioAutenticado)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Você só pode excluir a sua própria conta.");
        }

        Usuario usuario = repositorio.findByIdUsuario(id);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.");
        }

        // Exclusão lógica para preservar o histórico de selos e feedbacks.
        usuario.setStatusUsuario(0);
        usuario.setAtualizadoEm(LocalDateTime.now());
        repositorio.save(usuario);
    }
}
