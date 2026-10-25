package com.hachi.api.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
public class GerenciadorTokenJwt {
    private final SecretKey chave;
    private final long validadeMinutos;

    public GerenciadorTokenJwt(
            @Value("${app.jwt.secret}") String segredo,
            @Value("${app.jwt.expiration-minutes:60}") long validadeMinutos) {
        byte[] bytes = segredo.getBytes(StandardCharsets.UTF_8);
        if (bytes.length < 32) {
            throw new IllegalArgumentException("A configuração app.jwt.secret deve conter pelo menos 32 bytes.");
        }
        this.chave = Keys.hmacShaKeyFor(bytes);
        this.validadeMinutos = validadeMinutos;
    }

    public String gerarToken(Integer idUsuario, String email, Integer funcao) {
        Instant agora = Instant.now();
        return Jwts.builder()
                .subject(idUsuario.toString())
                .claim("email", email)
                .claim("funcao", funcao)
                .issuedAt(Date.from(agora))
                .expiration(Date.from(agora.plusSeconds(validadeMinutos * 60)))
                .signWith(chave)
                .compact();
    }

    public Claims obterDadosToken(String token) {
        return Jwts.parser().verifyWith(chave).build().parseSignedClaims(token).getPayload();
    }

    public boolean validarToken(String token) {
        try {
            obterDadosToken(token);
            return true;
        } catch (RuntimeException excecao) {
            return false;
        }
    }

    public Integer obterIdUsuario(String token) {
        return Integer.valueOf(obterDadosToken(token).getSubject());
    }

    public long obterValidadeMinutos() {
        return validadeMinutos;
    }
}