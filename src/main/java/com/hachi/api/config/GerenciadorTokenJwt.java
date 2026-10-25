package com.hachi.api.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class GerenciadorTokenJwt {
    private final String segredo;
    private final long validadeMinutos;

    public GerenciadorTokenJwt(
            @Value("${app.jwt.secret}") String segredo,
            @Value("${app.jwt.expiration-minutes:60}") long validadeMinutos) {
        this.segredo = segredo;
        this.validadeMinutos = validadeMinutos;
        // Valida a configuração logo na inicialização usando o mesmo decodificador do exemplo JWT.
        parseSecret();
    }

    public String gerarToken(Integer idUsuario, String email, Integer funcao) {
        long agora = System.currentTimeMillis();
        return Jwts.builder()
                .subject(idUsuario.toString())
                .claim("email", email)
                .claim("funcao", funcao)
                .issuedAt(new Date(agora))
                .expiration(new Date(agora + validadeMinutos * 60_000))
                .signWith(parseSecret())
                .compact();
    }

    public Claims obterDadosToken(String token) {
        return Jwts.parser()
                .verifyWith(parseSecret())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validarToken(String token) {
        try {
            Claims dados = obterDadosToken(token);
            return dados.getExpiration() != null
                    && !dados.getExpiration().before(new Date(System.currentTimeMillis()));
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

    private SecretKey parseSecret() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(segredo));
    }
}