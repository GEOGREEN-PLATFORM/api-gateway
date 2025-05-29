package com.example.api_gateway.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private static final String[] SWAGGER_URLS = {
            "/webjars/swagger-ui/**",
            "/v3/api-docs/swagger-config",
            "/geospatial-server/v3/api-docs",
            "/user-server/v3/api-docs",
            "/file-server/v3/api-docs",
            "/collect-user-data-server/v3/api-docs",
            "/event-manager-server/v3/api-docs",
            "/photo-analyser-server/v3/api-docs",
            "/notification-server/v3/api-docs",
            "/report-server/v3/api-docs"
    };

    private static final String[] ALLOWED_URLS = {
            "/geo/dict/**",
            "/geo/info/{geoPointId}",
            "/geo/info/getAll",
            "/geo/info/getAll/{problemAreaType}",
            "/user/register/forgot-password/**",
            "/user/register/user",
            "/file/image/**",
            "/realms/**",
            "auth/**",
            "/realms/geogreen/**",
            "/actuator/**",
            "/realms/**",
            "/admin/**",
            "/auth/**",
            "/admin/realms/geogreen/users"
    };

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeExchange(
                        request -> request
                                .pathMatchers("/error").permitAll()
                                .pathMatchers(ALLOWED_URLS).permitAll()
                                .pathMatchers(SWAGGER_URLS).permitAll()
                                .anyExchange().authenticated()
                )
                .oauth2ResourceServer((oauth2) -> oauth2
                        .jwt(Customizer.withDefaults())
                );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://217.198.13.249:30099", "http://localhost:7894"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}