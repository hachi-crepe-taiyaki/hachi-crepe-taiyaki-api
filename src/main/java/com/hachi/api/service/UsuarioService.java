package com.hachi.api.service;

import com.hachi.api.Entity.Usuario;
import com.hachi.api.config.GerenciadorTokenJwt;
import com.hachi.api.dto.LoginDTO;
import com.hachi.api.dto.ResultadoLoginDTO;
import com.hachi.api.dto.UsuarioAtualizacaoDTO;
import com.hachi.api.dto.UsuarioCadastroDTO;
import com.hachi.api.dto.UsuarioRespostaDTO;
import com.hachi.api.mapper.UsuarioMapper;
import com.hachi.api.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

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
        if (repositorio.contarPorEmail(dto.getEmail().trim()) > 0) {
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
        Usuario usuario = repositorio.buscarPorEmail(dto.getEmail().trim());
        if (usuario == null || !codificadorSenha.matches(dto.getSenha(), usuario.getSenha())
                || (usuario.getStatusUsuario() != null && usuario.getStatusUsuario() != 1)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "E-mail ou senha inválidos.");
        }

        String token = gerenciadorTokenJwt.gerarToken(
                usuario.getIdUsuario(), usuario.getEmail(), usuario.getFuncao());
        return new ResultadoLoginDTO(token, mapeador.paraResposta(usuario));
    }

    @Transactional
    public UsuarioRespostaDTO atualizarUsuario(Integer id, UsuarioAtualizacaoDTO dto,
                                                Integer idUsuarioAutenticado, boolean administrador) {
        if (!administrador && !id.equals(idUsuarioAutenticado)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Você só pode alterar os dados da sua própria conta.");
        }

        Usuario usuario = repositorio.buscarPorId(id);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.");
        }
        if (dto.getEmail() != null && repositorio.contarPorEmailDeOutroUsuario(dto.getEmail().trim(), id) > 0) {
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

        Usuario usuario = repositorio.buscarPorId(id);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.");
        }

        // Exclusão lógica para preservar o histórico de selos e feedbacks.
        usuario.setStatusUsuario(0);
        usuario.setAtualizadoEm(LocalDateTime.now());
        repositorio.save(usuario);
    }
}