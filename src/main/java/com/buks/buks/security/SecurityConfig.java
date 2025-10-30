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
                // 🔹 Habilita CORS e desativa CSRF
                .cors(cors -> {})  // habilita suporte ao CORS
                .csrf(csrf -> csrf.disable())

                // 🔹 Configura as permissões das rotas
                .authorizeHttpRequests(auth -> auth
                        // Rotas públicas
                        .requestMatchers("/api/auth/**").permitAll()

                        // GET de livros → USER ou ADMIN
                        .requestMatchers("/api/livros", "/api/livros/*").hasAnyRole("USER", "ADMIN")

                        // Modificações em livros → apenas ADMIN
                        .requestMatchers("/api/livros/**").hasRole("ADMIN")

                        // Qualquer outra rota precisa de autenticação
                        .anyRequest().authenticated()
                )

                // 🔹 Define política de sessão (sem estado)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 🔹 Adiciona o filtro JWT antes do de autenticação padrão
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 🔹 Configuração global de CORS
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        // frontend React
                        .allowedOrigins("http://localhost:3000")
                        // métodos aceitos
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        // permite headers personalizados (como Authorization)
                        .allowedHeaders("*")
                        // permite cookies e cabeçalhos de autenticação
                        .allowCredentials(true);
            }
        };
    }
}
