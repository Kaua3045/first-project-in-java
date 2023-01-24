package com.kaua.first.configuration.person;

import com.kaua.first.repositories.PersonRepository;
import com.kaua.first.security.JwtAuthenticationFilter;
import com.kaua.first.security.JwtService;
import com.kaua.first.services.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PersonConfiguration {

    @Bean
    public PersonService personServiceBean(
            PersonRepository personRepository,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        return new PersonService(
                personRepository,
                authenticationManager,
                jwtService,
                passwordEncoder
        );
    }

    @Bean
    public JwtService jwtServiceBean() {
        return new JwtService();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(PersonRepository personRepository) {
        return new JwtAuthenticationFilter(jwtServiceBean(), personRepository);
    }
}
