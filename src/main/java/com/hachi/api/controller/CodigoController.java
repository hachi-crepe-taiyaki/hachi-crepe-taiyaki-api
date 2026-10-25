package com.hachi.api.controller;

import com.hachi.api.dto.CodigoRespostaDTO;
import com.hachi.api.service.CodigoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/codigos")
public class CodigoController {
    private final CodigoService codigoService;

    public CodigoController(CodigoService codigoService) {
        this.codigoService = codigoService;
    }

    @PostMapping("/gerar")
    public ResponseEntity<CodigoRespostaDTO> gerarCodigo(Authentication autenticacao) {
        Integer idUsuarioAutenticado = obterIdAutenticado(autenticacao);

        boolean funcionarioNoToken = autenticacao.getAuthorities().stream()
                .anyMatch(autoridade -> autoridade.getAuthority().equals("ROLE_EMPLOYEE"));

        if (!funcionarioNoToken) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Somente funcionários podem gerar códigos.");
        }

        CodigoRespostaDTO resposta = codigoService.gerarCodigo(idUsuarioAutenticado);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
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
