package com.capstone.app.config;
import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {    
 @Bean    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http            .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity; enable in production
            .authorizeHttpRequests(auth -> auth                .requestMatchers("/api/clients/register").permitAll() // Allow unauthenticated access to registration
                .requestMatchers("/api/clients").permitAll()                .requestMatchers("/api/clients/{clientId}").permitAll() // Secure update client endpoint
                .requestMatchers("/api/clients/update/**").permitAll()                .requestMatchers("/{companyName}").permitAll()
                .requestMatchers("/find").permitAll()                .requestMatchers("/api/transactions").permitAll()
                .requestMatchers("/api/employees/**").permitAll()                .anyRequest().authenticated() // Secure any other endpoints
            );
        return http.build();    }
        @Bean
    public PasswordEncoder passwordEncoder() {        return new BCryptPasswordEncoder(); // Define PasswordEncoder bean
    }}