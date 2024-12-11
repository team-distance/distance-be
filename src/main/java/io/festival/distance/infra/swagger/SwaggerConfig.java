package io.festival.distance.infra.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "swagger.enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerConfig {
    SecurityScheme apiKey = new SecurityScheme()
        .type(SecurityScheme.Type.APIKEY)
        .in(SecurityScheme.In.HEADER)
        .name("Authorization");

    SecurityRequirement securityRequirement = new SecurityRequirement()
        .addList("Bearer Token");
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .components(new Components().addSecuritySchemes("Bearer Token",apiKey))
            .addSecurityItem(securityRequirement)
            .info(new Info()
                .title("Team-Distance")
                .version("1.0.0")
                .description("distance API명세서입니다!")
            )
            .servers(List.of(
                new Server().url("https://dev.dis-tance.com"),
                //new Server().url("https://api.dis-tance.com"),
                new Server().url("http://localhost:8080")
            ));
    }

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
            .group("인증이 필요한 API")
            .pathsToExclude("/api/login/**", "/api/member/signup/**","/api/admin/signup/**")
            .build();
    }

    @Bean
    public GroupedOpenApi nonAuthApi() {
        return GroupedOpenApi.builder()
            .group("인증이 불필요한 API")
            .pathsToMatch("/api/login/**", "/api/member/signup/**","/api/admin/signup/**")
            .build();
    }
}
