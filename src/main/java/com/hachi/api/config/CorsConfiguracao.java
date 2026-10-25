package com.hachi.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfiguracao {
    @Bean
    public CorsConfigurationSource configuracaoCors(
            @Value("${cors.allowedOrigins:http://localhost:5173}") List<String> origensPermitidas) {
        CorsConfiguration configuracao = new CorsConfiguration();
        configuracao.setAllowedOrigins(origensPermitidas);
        configuracao.setAllowCredentials(true);
        configuracao.setAllowedHeaders(List.of("*"));
        configuracao.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name()
        ));
        configuracao.setExposedHeaders(List.of(HttpHeaders.CONTENT_DISPOSITION));

        UrlBasedCorsConfigurationSource origem = new UrlBasedCorsConfigurationSource();
        origem.registerCorsConfiguration("/**", configuracao);
        return origem;
    }
}