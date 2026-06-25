package com.srgec.quiz_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Allow static files — HTML pages
                .requestMatchers(
                    "/",
                    "/index.html",
                    "/quizzes.html",
                    "/quiz.html",
                    "/*.html",
                    "/*.css",
                    "/*.js"
                ).permitAll()
                // Allow public API endpoints
                .requestMatchers(
                    "/register",
                    "/login",
                    "/h2-console/**"
                ).permitAll()
                // Everything else needs JWT token
                .anyRequest().authenticated()
            )
            .headers(headers -> headers.frameOptions(f -> f.disable()))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}