package com.buks.buks.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Habilita CORS e desativa CSRF
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())

                // Configuração de permissões das rotas
                .authorizeHttpRequests(auth -> auth
                        // Rotas públicas
                        .requestMatchers("/api/auth/**").permitAll()

                        // 📚 Rotas de livros
                        .requestMatchers("/api/livros").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/livros/**").hasRole("ADMIN")

                        // ⭐ Rotas de avaliações
                        // Qualquer usuário autenticado (USER ou ADMIN) pode listar e criar avaliações
                        .requestMatchers("/api/avaliacoes", "/api/avaliacoes/**").hasAnyRole("USER", "ADMIN")

                        // Qualquer outra rota precisa estar autenticada
                        .anyRequest().authenticated()
                )

                // Sessão sem estado (JWT)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Adiciona o filtro JWT antes do UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Configuração global de CORS
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        // endereço do frontend (ajuste se for diferente)
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
