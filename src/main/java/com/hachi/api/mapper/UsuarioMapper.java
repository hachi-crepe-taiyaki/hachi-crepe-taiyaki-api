package com.hachi.api.Mapper;

import com.hachi.api.Entity.Usuario;
import com.hachi.api.DTO.UsuarioRespostaDTO;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public UsuarioRespostaDTO paraResposta(Usuario usuario) {
        return new UsuarioRespostaDTO(
                usuario.getIdUsuario(), usuario.getNome(), usuario.getEmail(), usuario.getTelefone(),
                usuario.getCriadoEm(), usuario.getAtualizadoEm(), usuario.getStatusUsuario(),
                usuario.getFuncao(), usuario.getDtNasc(), usuario.getCidade(), usuario.getRegiao(),
                usuario.getSelos()
        );
    }
}