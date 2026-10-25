package com.hachi.api.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Hachi Loyalty API",
        description = "API do Hachi Crepe e Taiyaki para cadastro e autenticação de usuários, consulta e manutenção do cardápio, geração e resgate de códigos de fidelidade, resgate de recompensas e envio e consulta de feedbacks. Endpoints protegidos usam o cookie HttpOnly hachi_access_token, contendo um JWT.",
        version = "1.0.0"
))
@SecurityScheme(
        name = "cookieAuth",
        type = SecuritySchemeType.APIKEY,
        in = io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.COOKIE,
        paramName = "hachi_access_token",
        description = "Cookie de autenticação HttpOnly emitido pelo endpoint POST /api/usuarios/login. O navegador o envia automaticamente nas requisições ao domínio da API; endpoints protegidos exigem um token válido."
)
public class OpenApiConfig {
}