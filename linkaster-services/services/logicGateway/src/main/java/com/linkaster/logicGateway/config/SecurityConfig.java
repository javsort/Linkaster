package com.linkaster.logicGateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity; 
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.linkaster.logicGateway.filter.JwtFilter;

/*
 * This class configures the security of the application.
 * It allows login without authentication and requires authentication for all other requests.
 * It also disables CSRF and sets the session creation policy to STATELESS.
*/

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Filter for JWT token verification
    @Autowired
    private JwtFilter jwtFilter;

    // Security filter chain configuration
    @Bean
    protected SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/api/login", "/api/status","/auth/login").permitAll() // Allow login without authentication
                .anyRequest().authenticated()                                                       // All other requests require authentication
            )
            .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}