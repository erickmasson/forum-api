package com.projeto.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    // Rota de Login
                    req.requestMatchers(HttpMethod.POST, "/login").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/users").permitAll();

                    req.requestMatchers(HttpMethod.GET, "/topics/**").permitAll();
                    req.requestMatchers(HttpMethod.GET, "/replies/**").permitAll();

                    // Console do H2
                    req.requestMatchers("/h2-console/**").permitAll();

                    // DOCUMENTAÇÃO (SWAGGER/OPENAPI) - Ajuste aqui:
                    req.requestMatchers(
                            "/v3/api-docs/**",    // Sub-arquivos
                            "/v3/api-docs",       // O arquivo JSON principal que deu erro 403
                            "/swagger-ui/**",     // Interface visual
                            "/swagger-ui.html"    // Página inicial do Swagger
                    ).permitAll();

                    // Todo o resto exige autenticação
                    req.anyRequest().authenticated();
                })
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}