package com.hachi.api.Service;

import com.hachi.api.Entity.Usuario;
import com.hachi.api.Repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AutorizacaoAdminService {
    private final UsuarioRepository usuarioRepositorio;

    public AutorizacaoAdminService(UsuarioRepository usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Transactional(readOnly = true)
    public void validarAdministrador(Integer idUsuarioAutenticado) {
        if (idUsuarioAutenticado == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "É necessário estar autenticado para gerenciar o cardápio.");
        }

        Usuario usuario = usuarioRepositorio.findByIdUsuario(idUsuarioAutenticado);
        if (usuario == null || !Integer.valueOf(1).equals(usuario.getStatusUsuario())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "O usuário autenticado não existe ou está inativo.");
        }

        if (!Integer.valueOf(2).equals(usuario.getFuncao())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Apenas administradores podem editar o cardápio.");
        }
    }
}
