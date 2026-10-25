package com.hachi.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    OpenAPI hachiOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Hachi Loyalty API").version("v1")
                        .description("REST API for the Hachi Crepe e Taiyaki loyalty program."))
                .schemaRequirement("cookieAuth", new SecurityScheme()
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.COOKIE)
                        .name("hachi_access_token"))
                .addSecurityItem(new SecurityRequirement().addList("cookieAuth"));
    }
}