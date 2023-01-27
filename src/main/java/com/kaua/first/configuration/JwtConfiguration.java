package com.kaua.first.configuration;

import com.kaua.first.repositories.PersonRepository;
import com.kaua.first.security.JwtAuthenticationFilter;
import com.kaua.first.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfiguration {

    @Value("${spring.jwt.secret}")
    private String SECRET;

    @Bean
    public JwtService jwtServiceBean() {
        return new JwtService(SECRET);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(PersonRepository personRepository) {
        return new JwtAuthenticationFilter(jwtServiceBean(), personRepository);
    }
}
