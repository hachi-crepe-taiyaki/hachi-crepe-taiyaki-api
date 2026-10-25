package com.hachi.api.controller;

import com.hachi.api.service.ResgateSeloService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/usuarios")
public class ResgateSeloController {
    private final ResgateSeloService resgateSeloService;

    public ResgateSeloController(ResgateSeloService resgateSeloService) {
        this.resgateSeloService = resgateSeloService;
    }

    @PostMapping("/{id}/resgatarSelo/{codigo}")
    public ResponseEntity<Void> resgatarSelo(@PathVariable Integer id,
                                              @PathVariable String codigo,
                                              Authentication autenticacao) {
        Integer idUsuarioAutenticado = obterIdAutenticado(autenticacao);
        resgateSeloService.resgatarSelo(idUsuarioAutenticado, id, codigo);
        return ResponseEntity.noContent().build();
    }

    private Integer obterIdAutenticado(Authentication autenticacao) {
        if (autenticacao == null || autenticacao.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "É necessário estar autenticado.");
        }

        try {
            return Integer.valueOf(autenticacao.getName());
        } catch (NumberFormatException excecao) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "A autenticação é inválida.");
        }
    }
}
