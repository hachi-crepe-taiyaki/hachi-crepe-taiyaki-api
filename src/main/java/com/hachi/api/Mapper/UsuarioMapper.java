package com.hachi.api.Mapper;

import com.hachi.api.DTO.UsuarioRequestDTO;
import com.hachi.api.DTO.UsuarioResponseDTO;
import com.hachi.api.Entity.Usuario;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRequestDTO dto) {
        if (dto == null) return null;

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha()); // A password será tratada/hashada no Service
        usuario.setTelefone(dto.getTelefone());
        usuario.setStatusUsuario(dto.getStatusUsuario());
        usuario.setFuncao(dto.getFuncao());
        usuario.setDtNasc(dto.getDtNasc());
        usuario.setCidade(dto.getCidade());
        usuario.setRegiao(dto.getRegiao());
        usuario.setSelos(dto.getSelos());
        return usuario;
    }

    public static UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        if (usuario == null) return null;

        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setTelefone(usuario.getTelefone());
        dto.setCriadoEm(usuario.getCriadoEm());
        dto.setAtualizadoEm(usuario.getAtualizadoEm());
        dto.setStatusUsuario(usuario.getStatusUsuario());
        dto.setFuncao(usuario.getFuncao());
        dto.setDtNasc(usuario.getDtNasc());
        dto.setCidade(usuario.getCidade());
        dto.setRegiao(usuario.getRegiao());
        dto.setSelos(usuario.getSelos());
        return dto;
    }

}
