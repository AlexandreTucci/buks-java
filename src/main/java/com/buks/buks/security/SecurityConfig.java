package com.buks.buks.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Rotas públicas
                        .requestMatchers("/api/auth/**").permitAll()

                        // GET de livros → USER ou ADMIN
                        .requestMatchers("/api/livros", "/api/livros/*").hasAnyRole("USER", "ADMIN")

                        // Rotas que modificam livros → apenas ADMIN
                        .requestMatchers("/api/livros/**").hasRole("ADMIN")

                        // Qualquer outra rota precisa de autenticação
                        .anyRequest().authenticated()
                )
                // Sem sessão, JWT é suficiente
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Filtro JWT
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
