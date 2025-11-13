package com.buks.buks.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                .cors(cors -> {
                })
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                                .requestMatchers(
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html"
                                ).permitAll()

                                // 🔓 Rotas públicas (login/registro)
                                .requestMatchers("/api/auth/**").permitAll()

                                // 📚 LIVROS
                                // GET pode ser feito por qualquer usuário autenticado
                                .requestMatchers(HttpMethod.GET, "/api/livros/**").hasAnyRole("USER", "ADMIN")
                                // POST, PUT e DELETE apenas ADMIN
                                .requestMatchers(HttpMethod.POST, "/api/livros/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/livros/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/livros/**").hasRole("ADMIN")

                                // ⭐ AVALIAÇÕES
                                // Usuário autenticado pode criar e listar
                                .requestMatchers(HttpMethod.GET, "/api/avaliacoes/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/avaliacoes/**").hasAnyRole("USER", "ADMIN")
                                // Apenas ADMIN pode deletar ou atualizar
                                .requestMatchers(HttpMethod.PUT, "/api/avaliacoes/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/avaliacoes/**").hasRole("ADMIN")

                                // 🧾 PEDIDOS
                                // Usuário autenticado pode listar e criar pedidos
                                .requestMatchers(HttpMethod.GET, "/api/pedidos/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/pedidos/**").hasAnyRole("USER", "ADMIN")
                                // Apenas ADMIN pode atualizar ou deletar
                                .requestMatchers(HttpMethod.PUT, "/api/pedidos/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/pedidos/**").hasRole("ADMIN")

                                // 💲 PAGAMENTOS
                                // Usuário pode pagar e ver seus pagamentos
                                .requestMatchers(HttpMethod.POST, "/api/pagamentos/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/pagamentos/**").hasAnyRole("USER", "ADMIN")
                                // Apenas ADMIN altera ou deleta histórico de pagamento
                                .requestMatchers(HttpMethod.PUT, "/api/pagamentos/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/pagamentos/**").hasRole("ADMIN")

                                // 🔒 Outras rotas requerem autenticação
                                .anyRequest().authenticated()
                )

                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 🌐 CORS
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("http://localhost:3000")
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                        .allowedHeaders("*")
//                        .allowCredentials(true);
//            }
//        };
//    }
}
