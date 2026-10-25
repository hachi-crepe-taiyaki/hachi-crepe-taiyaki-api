package com.hachi.api.service;

import com.hachi.api.Entity.Usuario;
import com.hachi.api.dto.LoginDTO;
import com.hachi.api.dto.UsuarioAtualizacaoDTO;
import com.hachi.api.dto.UsuarioCadastroDTO;
import com.hachi.api.dto.UsuarioRespostaDTO;
import com.hachi.api.mapper.UsuarioMapper;
import com.hachi.api.repository.UsuarioRepository;
import com.hachi.api.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper mapper;
    private final JwtService jwtService;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder,
                          UsuarioMapper mapper, JwtService jwtService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
        this.jwtService = jwtService;
    }

    @Transactional
    public UsuarioRespostaDTO cadastrar(UsuarioCadastroDTO dto) {
        if (repository.existsByEmailIgnoreCase(dto.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered.");
        }
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome().trim());
        usuario.setEmail(dto.email().trim().toLowerCase());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setTelefone(dto.telefone());
        usuario.setDtNasc(dto.dtNasc());
        usuario.setCidade(dto.cidade());
        usuario.setRegiao(dto.regiao());
        usuario.setFuncao(0);
        usuario.setStatusUsuario(1);
        usuario.setSelos(0);
        usuario.setCriadoEm(LocalDateTime.now());
        usuario.setAtualizadoEm(LocalDateTime.now());
        return mapper.toResponse(repository.save(usuario));
    }

    public LoginResult login(LoginDTO dto) {
        Usuario usuario = repository.findByEmailIgnoreCase(dto.email().trim())
                .filter(u -> passwordEncoder.matches(dto.senha(), u.getSenha()))
                .filter(u -> u.getStatusUsuario() == null || u.getStatusUsuario() == 1)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials."));
        String token = jwtService.generateToken(usuario.getIdUsuario(), usuario.getEmail(), usuario.getFuncao());
        return new LoginResult(token, mapper.toResponse(usuario));
    }

    @Transactional
    public UsuarioRespostaDTO atualizar(Integer id, UsuarioAtualizacaoDTO dto, Integer authenticatedId, boolean admin) {
        if (!admin && !id.equals(authenticatedId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You may only update your own account.");
        }
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        if (dto.email() != null && repository.existsByEmailIgnoreCaseAndIdUsuarioNot(dto.email().trim(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered.");
        }
        if (dto.nome() != null) usuario.setNome(dto.nome().trim());
        if (dto.email() != null) usuario.setEmail(dto.email().trim().toLowerCase());
        if (dto.senha() != null && !dto.senha().isBlank()) usuario.setSenha(passwordEncoder.encode(dto.senha()));
        if (dto.telefone() != null) usuario.setTelefone(dto.telefone());
        if (dto.dtNasc() != null) usuario.setDtNasc(dto.dtNasc());
        if (dto.cidade() != null) usuario.setCidade(dto.cidade());
        if (dto.regiao() != null) usuario.setRegiao(dto.regiao());
        // Role, status, and loyalty balance are intentionally not client-editable.
        usuario.setAtualizadoEm(LocalDateTime.now());
        return mapper.toResponse(repository.save(usuario));
    }

    @Transactional
    public void excluir(Integer id, Integer authenticatedId, boolean admin) {
        if (!admin && !id.equals(authenticatedId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You may only delete your own account.");
        }
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        // Soft-delete to preserve foreign-key-linked feedback and loyalty history.
        usuario.setStatusUsuario(0);
        usuario.setAtualizadoEm(LocalDateTime.now());
        repository.save(usuario);
    }

    public record LoginResult(String token, UsuarioRespostaDTO usuario) {}
}