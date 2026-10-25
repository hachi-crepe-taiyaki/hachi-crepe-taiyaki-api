package com.hachi.api.service;

import com.hachi.api.Entity.Codigo;
import com.hachi.api.Entity.ResgateSelo;
import com.hachi.api.Entity.Usuario;
import com.hachi.api.repository.CodigoRepository;
import com.hachi.api.repository.ResgateSeloRepository;
import com.hachi.api.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class ResgateSeloService {
    private final UsuarioRepository usuarioRepositorio;
    private final CodigoRepository codigoRepositorio;
    private final ResgateSeloRepository resgateSeloRepositorio;

    public ResgateSeloService(UsuarioRepository usuarioRepositorio,
                              CodigoRepository codigoRepositorio,
                              ResgateSeloRepository resgateSeloRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.codigoRepositorio = codigoRepositorio;
        this.resgateSeloRepositorio = resgateSeloRepositorio;
    }

    @Transactional
    public void resgatarSelo(Integer idUsuarioAutenticado, Integer idUsuarioSolicitado, String codigoInformado) {
        if (idUsuarioAutenticado == null || idUsuarioSolicitado == null
                || !idUsuarioAutenticado.equals(idUsuarioSolicitado)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Você só pode resgatar selos para a sua própria conta.");
        }

        Usuario usuario = usuarioRepositorio.findByIdUsuario(idUsuarioSolicitado);
        if (usuario == null || !Integer.valueOf(1).equals(usuario.getStatusUsuario())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Usuário não encontrado ou inativo.");
        }

        if (codigoInformado == null || codigoInformado.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O código do selo é obrigatório.");
        }

        Codigo codigo = codigoRepositorio.findByCodigoIgnoreCase(codigoInformado.trim());
        if (codigo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Código de selo não encontrado.");
        }

        if (!Integer.valueOf(0).equals(codigo.getStatusScan())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Este código já foi utilizado.");
        }

        ResgateSelo resgateSelo = new ResgateSelo();
        resgateSelo.setCodigo(codigo);
        resgateSelo.setUsuario(usuario);
        resgateSelo.setDataHora(LocalDateTime.now());
        resgateSeloRepositorio.save(resgateSelo);

        usuario.setSelos((usuario.getSelos() == null ? 0 : usuario.getSelos()) + 1);
        usuarioRepositorio.save(usuario);

        codigo.setStatusScan(1);
        codigoRepositorio.save(codigo);
    }
}
