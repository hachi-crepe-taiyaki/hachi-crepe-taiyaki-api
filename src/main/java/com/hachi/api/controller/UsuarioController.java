package com.hachi.api.controller;

import com.hachi.api.dto.LoginDTO;
import com.hachi.api.dto.UsuarioAtualizacaoDTO;
import com.hachi.api.dto.UsuarioCadastroDTO;
import com.hachi.api.dto.UsuarioRespostaDTO;
import com.hachi.api.security.JwtAuthenticationFilter;
import com.hachi.api.service.UsuarioService;
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
    private final UsuarioService service;
    private final boolean secureCookie;
    private final long jwtExpirationMinutes;

    public UsuarioController(UsuarioService service,
                             @Value("${app.cookie.secure:false}") boolean secureCookie,
                             @Value("${app.jwt.expiration-minutes:60}") long jwtExpirationMinutes) {
        this.service = service;
        this.secureCookie = secureCookie;
        this.jwtExpirationMinutes = jwtExpirationMinutes;
    }

    @PostMapping
    public ResponseEntity<UsuarioRespostaDTO> cadastrar(@Valid @RequestBody UsuarioCadastroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioRespostaDTO> login(@Valid @RequestBody LoginDTO dto,
                                                     HttpServletResponse response) {
        UsuarioService.LoginResult result = service.login(dto);
        response.addHeader("Set-Cookie", tokenCookie(result.token(), Duration.ofMinutes(jwtExpirationMinutes)).toString());
        return ResponseEntity.ok(result.usuario());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        response.addHeader("Set-Cookie", tokenCookie("", Duration.ZERO).toString());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioRespostaDTO> atualizar(@PathVariable Integer id,
                                                         @Valid @RequestBody UsuarioAtualizacaoDTO dto,
                                                         Authentication authentication) {
        Integer authenticatedId = authenticatedId(authentication);
        boolean admin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        return ResponseEntity.ok(service.atualizar(id, dto, authenticatedId, admin));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id, Authentication authentication) {
        Integer authenticatedId = authenticatedId(authentication);
        boolean admin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        service.excluir(id, authenticatedId, admin);
        return ResponseEntity.noContent().build();
    }

    private Integer authenticatedId(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication required.");
        }
        try {
            return Integer.valueOf(authentication.getName());
        } catch (NumberFormatException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid authentication.");
        }
    }

    private ResponseCookie tokenCookie(String token, Duration maxAge) {
        return ResponseCookie.from(JwtAuthenticationFilter.COOKIE_NAME, token)
                .httpOnly(true)
                .secure(secureCookie)
                .sameSite("Strict")
                .path("/")
                .maxAge(maxAge)
                .build();
    }
}