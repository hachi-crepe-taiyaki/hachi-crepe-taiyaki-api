package com.hachi.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.hachi.api.dto.CodigoRespostaDTO;
import com.hachi.api.dto.CodigoGeracaoDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import com.hachi.api.service.CodigoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Tag(name = "Códigos de fidelidade", description = "Geração de códigos de compra para concessão de selos do programa de fidelidade.")
@RestController
@RequestMapping("/api/codigos")
public class CodigoController {
    private final CodigoService codigoService;

    public CodigoController(CodigoService codigoService) {
        this.codigoService = codigoService;
    }

    @Operation(summary = "Gerar código de fidelidade"
    @SecurityRequirement(name = "cookieAuth"), description = "Gera um código de uso único associado ao valor de uma compra. Exige autenticação e papel de funcionário. O serviço valida o valor permitido e registra o código para posterior resgate pelo cliente.")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Código gerado."), @ApiResponse(responseCode = "400", description = "Valor da compra inválido."), @ApiResponse(responseCode = "401", description = "Usuário não autenticado."), @ApiResponse(responseCode = "403", description = "Somente funcionários podem gerar códigos.")})
    @PostMapping("/gerar")
    public ResponseEntity<CodigoRespostaDTO> gerarCodigo(Authentication autenticacao, @Valid @RequestBody CodigoGeracaoDTO requisicao) {
        Integer idUsuarioAutenticado = obterIdAutenticado(autenticacao);

        boolean funcionarioNoToken = autenticacao.getAuthorities().stream()
                .anyMatch(autoridade -> autoridade.getAuthority().equals("ROLE_EMPLOYEE"));

        if (!funcionarioNoToken) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Somente funcionários podem gerar códigos.");
        }

        CodigoRespostaDTO resposta = codigoService.gerarCodigo(idUsuarioAutenticado, requisicao.getValor());
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
