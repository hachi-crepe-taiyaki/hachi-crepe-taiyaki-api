package com.hachi.api.service;

import com.hachi.api.Entity.ResgateItem;
import com.hachi.api.Entity.Usuario;
import com.hachi.api.repository.ResgateItemRepository;
import com.hachi.api.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class ResgateItemService {
    private static final int SELOS_NECESSARIOS = 12;

    private final UsuarioRepository usuarioRepositorio;
    private final ResgateItemRepository resgateItemRepositorio;

    public ResgateItemService(UsuarioRepository usuarioRepositorio,
                              ResgateItemRepository resgateItemRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.resgateItemRepositorio = resgateItemRepositorio;
    }

    @Transactional
    public void resgatarItem(Integer idUsuarioAutenticado, Integer idUsuarioSolicitado) {
        if (idUsuarioAutenticado == null || idUsuarioSolicitado == null
                || !idUsuarioAutenticado.equals(idUsuarioSolicitado)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Você só pode resgatar um item para a sua própria conta.");
        }

        Usuario usuario = usuarioRepositorio.findByIdUsuario(idUsuarioSolicitado);
        if (usuario == null || !Integer.valueOf(1).equals(usuario.getStatusUsuario())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Usuário não encontrado ou inativo.");
        }

        int selosAtuais = usuario.getSelos() == null ? 0 : usuario.getSelos();
        if (selosAtuais < SELOS_NECESSARIOS) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Você precisa de pelo menos 12 selos para resgatar um item.");
        }

        ResgateItem resgateItem = new ResgateItem();
        resgateItem.setUsuario(usuario);
        resgateItem.setDataResgate(LocalDateTime.now());
        resgateItemRepositorio.save(resgateItem);

        usuario.setSelos(selosAtuais - SELOS_NECESSARIOS);
        usuario.setAtualizadoEm(LocalDateTime.now());
        usuarioRepositorio.save(usuario);
    }
}
