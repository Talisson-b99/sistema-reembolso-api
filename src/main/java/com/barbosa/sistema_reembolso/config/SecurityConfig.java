package com.barbosa.sistema_reembolso.config;


import com.barbosa.sistema_reembolso.security.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private AuthFilter authFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/usuarios", "POST").permitAll()
                        .requestMatchers("/reembolsos/**", "GET").hasAnyRole("GESTOR", "COLABORADOR")
                        .requestMatchers("/reembolsos").hasRole("GESTOR")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(authFilter,  UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
