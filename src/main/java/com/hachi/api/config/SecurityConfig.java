package com.hachi.api.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    PasswordEncoder codificadorSenha() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AutenticacaoFilter filtroAutenticacao(GerenciadorTokenJwt gerenciadorTokenJwt) {
        return new AutenticacaoFilter(gerenciadorTokenJwt);
    }

    @Bean
    ValidadorOrigemFilter filtroValidacaoOrigem(
            @Value("${app.cors.allowed-origins:http://localhost:3000,http://localhost:5173,http://localhost:5500}") String origens) {
        return new ValidadorOrigemFilter(origens);
    }

    @Bean
    FilterRegistrationBean<AutenticacaoFilter> desativarRegistroServletAutenticacao(AutenticacaoFilter filtro) {
        FilterRegistrationBean<AutenticacaoFilter> registro = new FilterRegistrationBean<>(filtro);
        registro.setEnabled(false);
        return registro;
    }

    @Bean
    FilterRegistrationBean<ValidadorOrigemFilter> desativarRegistroServletOrigem(ValidadorOrigemFilter filtro) {
        FilterRegistrationBean<ValidadorOrigemFilter> registro = new FilterRegistrationBean<>(filtro);
        registro.setEnabled(false);
        return registro;
    }

    @Bean
    SecurityFilterChain cadeiaSeguranca(HttpSecurity http, AutenticacaoFilter filtroAutenticacao,
                                        ValidadorOrigemFilter filtroOrigem) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                // O filtro de origem protege requisições feitas por navegadores com cookies.
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessao -> sessao.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(configuracao -> configuracao.authenticationEntryPoint(
                        (requisicao, resposta, excecao) -> resposta.sendError(401,
                                "É necessário estar autenticado para acessar este recurso.")))
                .authorizeHttpRequests(autorizacao -> autorizacao
                        .requestMatchers("/api/usuarios", "/api/usuarios/login", "/swagger-ui/**",
                                "/swagger-ui.html", "/v3/api-docs/**", "/error").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(filtroAutenticacao, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filtroOrigem, AutenticacaoFilter.class)
                .build();
    }
}