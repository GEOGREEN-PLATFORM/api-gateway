package com.example.api_gateway.configuration;

import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springdoc.core.customizers.OpenApiCustomizer;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .authorizeExchange(
                        request -> request
                                .pathMatchers("/error").permitAll()
                                .anyExchange().permitAll()
                )
                .oauth2ResourceServer((oauth2) -> oauth2
                        .jwt(Customizer.withDefaults())
                );
        return http.build();
    }

    @Bean
    public GroupedOpenApi service1Api() {
        return GroupedOpenApi.builder()
                .group("service-1")
                .pathsToMatch("/service-1/**")
                .addOpenApiCustomizer(serverUrlCustomizer("/service-1"))
                .build();
    }

    @Bean
    public GroupedOpenApi service2Api() {
        return GroupedOpenApi.builder()
                .group("service-2")
                .pathsToMatch("/service-2/**")
                .addOpenApiCustomizer(serverUrlCustomizer("/service-2"))
                .build();
    }

    private OpenApiCustomizer serverUrlCustomizer(String prefix) {
        return openApi -> {
            openApi.setServers(List.of(new Server().url(prefix)));
        };
    }
}