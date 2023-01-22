package com.kaua.first.configuration.person;

import com.kaua.first.repositories.PersonRepository;
import com.kaua.first.services.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonConfiguration {

    @Bean
    public PersonService personServiceBean(PersonRepository personRepository) {
        return new PersonService(personRepository);
    }
}
