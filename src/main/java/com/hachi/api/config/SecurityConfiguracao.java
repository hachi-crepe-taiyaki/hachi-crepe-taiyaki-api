package com.hachi.api.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
public class SecurityConfiguracao {
    @Bean
    PasswordEncoder codificadorSenha() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AutenticacaoFilter filtroAutenticacao(GerenciadorTokenJwt gerenciadorTokenJwt) {
        return new AutenticacaoFilter(gerenciadorTokenJwt);
    }

    @Bean
    FilterRegistrationBean<AutenticacaoFilter> desativarRegistroServletAutenticacao(AutenticacaoFilter filtro) {
        FilterRegistrationBean<AutenticacaoFilter> registro = new FilterRegistrationBean<>(filtro);
        registro.setEnabled(false);
        return registro;
    }

    @Bean
    SecurityFilterChain cadeiaSeguranca(HttpSecurity http, AutenticacaoFilter filtroAutenticacao,
                                        AutenticacaoEntryPoint pontoEntradaAutenticacao) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessao -> sessao.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .headers(cabecalhos -> cabecalhos.frameOptions(frame -> frame.sameOrigin()))
                .exceptionHandling(configuracao -> configuracao
                        .authenticationEntryPoint(pontoEntradaAutenticacao)
                        .accessDeniedHandler((requisicao, resposta, excecao) ->
                                resposta.sendError(403, "Você não tem permissão para acessar este recurso.")))
                .authorizeHttpRequests(autorizacao -> autorizacao
                        .requestMatchers(HttpMethod.POST, "/api/usuarios", "/api/usuarios/login").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/categorias", "/api/categorias/**",
                                "/api/produtos", "/api/produtos/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**",
                                "/error", "/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(filtroAutenticacao, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
