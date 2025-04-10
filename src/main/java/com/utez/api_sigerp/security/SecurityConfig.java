package com.utez.api_sigerp.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Value("#{'${app.cors.allowed-origins}'.split(',')}")
    private List<String> allowedOrigins;

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter authenticationFilter;

    public SecurityConfig(UserDetailsService userDetailsService, JwtAuthenticationFilter authenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/ws/**", "/topic/**", "/app/**", "/user/**", "/queue/**", "/socket/**", "/sockjs-node/**", "/sockjs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login", "/api/empleado/login/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/empleado", "/api/producto/categoria/{idCategoria}","api/ordenes","/api/empleado/id/{id}","/api/empleado/{id}/mesas", "/api/categoria", "/api/categoria/**", "/api/producto", "/api/producto/**", "/api/mesas", "/api/mesas/{nombre}", "/api/mesas/**", "/api/resena", "/api/resena/empleado/", "/api/ordenes", "/api/ordenes/mesa/{mesaId}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/empleado/{nombre}", "api/ordenes").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/categoria", "/api/resena", "/api/ordenes", "/api/ordenes/{id}", "/api/ventas/nueva").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/mesas/**","/api/producto/{id}", "/api/categoria/{id}/estado","/api/producto/{id}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/ordenes{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/mesas/**", "/api/ventas/todas", "/api/mesas/{id}/orden").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = (UrlBasedCorsConfigurationSource) corsConfigurationSource();
        return new CorsFilter(source);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}