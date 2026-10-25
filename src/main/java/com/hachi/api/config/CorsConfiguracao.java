package com.hachi.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CorsConfiguracao {
    @Bean
    CorsConfigurationSource configuracaoCors(
            @Value("${app.cors.allowed-origins:http://localhost:3000,http://localhost:5173,http://localhost:5500}") String origens) {
        List<String> origensPermitidas = new ArrayList<>();
        for (String origem : origens.split(",")) {
            String origemLimpa = origem.trim();
            if (!origemLimpa.isEmpty()) {
                origensPermitidas.add(origemLimpa);
            }
        }

        CorsConfiguration configuracao = new CorsConfiguration();
        configuracao.setAllowedOrigins(origensPermitidas);
        configuracao.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuracao.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-CSRF-TOKEN"));
        configuracao.setAllowCredentials(true);
        configuracao.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource origem = new UrlBasedCorsConfigurationSource();
        origem.registerCorsConfiguration("/**", configuracao);
        return origem;
    }
}