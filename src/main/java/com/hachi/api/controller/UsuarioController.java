package com.hachi.api.controller;

import com.hachi.api.config.AutenticacaoFilter;
import com.hachi.api.config.GerenciadorTokenJwt;
import com.hachi.api.dto.CodigoResgateDTO;
import com.hachi.api.dto.LoginDTO;
import com.hachi.api.dto.ResultadoLoginDTO;
import com.hachi.api.dto.UsuarioAtualizacaoDTO;
import com.hachi.api.dto.UsuarioCadastroDTO;
import com.hachi.api.dto.UsuarioRespostaDTO;
import com.hachi.api.service.UsuarioService;
import com.hachi.api.service.ResgateSeloService;
import com.hachi.api.service.ResgateItemService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final ResgateSeloService resgateSeloService;
    private final ResgateItemService resgateItemService;
    private final boolean cookieSeguro;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;

    public UsuarioController(UsuarioService usuarioService,
                             ResgateSeloService resgateSeloService,
                             ResgateItemService resgateItemService,
                             GerenciadorTokenJwt gerenciadorTokenJwt,
                             @Value("${app.cookie.secure:false}") boolean cookieSeguro) {
        this.usuarioService = usuarioService;
        this.resgateSeloService = resgateSeloService;
        this.resgateItemService = resgateItemService;
        this.gerenciadorTokenJwt = gerenciadorTokenJwt;
        this.cookieSeguro = cookieSeguro;
    }

    @PostMapping
    public ResponseEntity<UsuarioRespostaDTO> cadastrarUsuario(@Valid @RequestBody UsuarioCadastroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrarUsuario(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioRespostaDTO> autenticarUsuario(@Valid @RequestBody LoginDTO dto,
                                                                 HttpServletResponse resposta) {
        ResultadoLoginDTO resultado = usuarioService.autenticarUsuario(dto);
        resposta.addHeader("Set-Cookie", criarCookieToken(resultado.getToken(),
                Duration.ofMinutes(gerenciadorTokenJwt.obterValidadeMinutos())).toString());
        return ResponseEntity.ok(resultado.getUsuario());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> encerrarSessao(HttpServletResponse resposta) {
        resposta.addHeader("Set-Cookie", criarCookieToken("", Duration.ZERO).toString());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/selos")
    public ResponseEntity<Void> resgatarSelo(@PathVariable Integer id,
                                              @Valid @RequestBody CodigoResgateDTO dto,
                                              Authentication autenticacao) {
        Integer idAutenticado = obterIdAutenticado(autenticacao);
        resgateSeloService.resgatarSelo(idAutenticado, id, dto.getCodigo());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/resgatarItem")
    public ResponseEntity<Void> resgatarItem(@PathVariable Integer id, Authentication autenticacao) {
        Integer idAutenticado = obterIdAutenticado(autenticacao);
        resgateItemService.resgatarItem(idAutenticado, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioRespostaDTO> atualizarUsuario(@PathVariable Integer id,
            @Valid @RequestBody UsuarioAtualizacaoDTO dto, Authentication autenticacao) {
        Integer idAutenticado = obterIdAutenticado(autenticacao);
        boolean administrador = autenticacao.getAuthorities().stream()
                .anyMatch(autoridade -> autoridade.getAuthority().equals("ROLE_ADMIN"));
        return ResponseEntity.ok(usuarioService.atualizarUsuario(id, dto, idAutenticado, administrador));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Integer id, Authentication autenticacao) {
        Integer idAutenticado = obterIdAutenticado(autenticacao);
        boolean administrador = autenticacao.getAuthorities().stream()
                .anyMatch(autoridade -> autoridade.getAuthority().equals("ROLE_ADMIN"));
        usuarioService.excluirUsuario(id, idAutenticado, administrador);
        return ResponseEntity.noContent().build();
    }

    private Integer obterIdAutenticado(Authentication autenticacao) {
        if (autenticacao == null || autenticacao.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "É necessário estar autenticado.");
        }
        try {
            return Integer.valueOf(autenticacao.getName());
        } catch (NumberFormatException excecao) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "A autenticação é inválida.");
        }
    }

    private ResponseCookie criarCookieToken(String token, Duration duracao) {
        return ResponseCookie.from(AutenticacaoFilter.COOKIE_NOME, token)
                .httpOnly(true)
                .secure(cookieSeguro)
                .sameSite("Strict")
                .path("/")
                .maxAge(duracao)
                .build();
    }
}