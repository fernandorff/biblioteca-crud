package com.pge.sisgal.infraestructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    private static final String BEARER_AUTH = "bearerAuth";

    @Bean
    public OpenAPI swagger() {
        return new OpenAPI()
            .info(new Info()
                .title("Sistema de Gestão de Aluguéis de Livros - SISGAL")
                .description("API para gerenciamento de livros, usuários e empréstimos. " +
                    "Para criar um novo usuário, use o endpoint POST /api/users com os dados desejados." +
                    "Para autenticar, use o endpoint POST /api/auth/login com 'registration' e 'password' " +
                    "para obter um JWT. Copie o token retornado e insira-o no botão 'Authorize'.")
                .version("1.0"))
            .addSecurityItem(new SecurityRequirement().addList(BEARER_AUTH))
            .components(new io.swagger.v3.oas.models.Components()
                .addSecuritySchemes(BEARER_AUTH, new SecurityScheme()
                    .name(BEARER_AUTH)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .description("Insira o token JWT obtido do endpoint /api/auth/login no formato 'Bearer <token>'. " +
                        "Exemplo: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...'")));
    }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/swagger-ui.html");
        registry.addRedirectViewController("/swagger-ui", "/swagger-ui.html");
    }
}
