package com.example.job_portal_backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String BEARER_KEY = "BearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Job Portal API")
                        .version("1.0")
                        .description("Documentation de l’API du portail d’emploi"))
                .addSecurityItem(new SecurityRequirement().addList(BEARER_KEY))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(BEARER_KEY, new SecurityScheme()
                                .name(BEARER_KEY)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
