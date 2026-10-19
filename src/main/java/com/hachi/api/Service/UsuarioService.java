package com.hachi.api.Service;

import com.hachi.api.DTO.UsuarioRequestDTO;
import com.hachi.api.DTO.UsuarioResponseDTO;
import com.hachi.api.Entity.Usuario;
import com.hachi.api.Mapper.UsuarioMapper;
import com.hachi.api.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO buscarPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilizador não encontrado com o ID: " + id));
        return UsuarioMapper.toResponseDTO(usuario);
    }

    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        Usuario usuario = UsuarioMapper.toEntity(dto);

        // Criptografar a password antes de persistir
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setCriadoEm(LocalDateTime.now());

        Usuario salvo = usuarioRepository.save(usuario);
        return UsuarioMapper.toResponseDTO(salvo);
    }

    public UsuarioResponseDTO atualizar(Integer id, UsuarioRequestDTO dto) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilizador não encontrado com o ID: " + id));

        usuarioExistente.setNome(dto.getNome());
        usuarioExistente.setEmail(dto.getEmail());
        usuarioExistente.setTelefone(dto.getTelefone());
        usuarioExistente.setStatusUsuario(dto.getStatusUsuario());
        usuarioExistente.setFuncao(dto.getFuncao());
        usuarioExistente.setDtNasc(dto.getDtNasc());
        usuarioExistente.setCidade(dto.getCidade());
        usuarioExistente.setRegiao(dto.getRegiao());
        usuarioExistente.setSelos(dto.getSelos());
        usuarioExistente.setAtualizadoEm(LocalDateTime.now());

        // Atualizar a password apenas se for enviada na requisição
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuarioExistente.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        Usuario atualizado = usuarioRepository.save(usuarioExistente);
        return UsuarioMapper.toResponseDTO(atualizado);
    }

    public void eliminar(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Utilizador não encontrado com o ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

}
