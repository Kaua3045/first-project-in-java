package com.kaua.first.configuration;

import com.kaua.first.repositories.PersonRepository;
import com.kaua.first.security.JwtAuthenticationFilter;
import com.kaua.first.security.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfiguration {

    @Bean
    public JwtService jwtServiceBean() {
        return new JwtService();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(PersonRepository personRepository) {
        return new JwtAuthenticationFilter(jwtServiceBean(), personRepository);
    }
}
