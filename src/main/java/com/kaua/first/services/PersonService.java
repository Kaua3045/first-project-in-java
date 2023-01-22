package com.kaua.first.services;

import com.kaua.first.entities.PersonEntity;
import com.kaua.first.repositories.PersonRepository;
import com.kaua.first.services.interfaces.PersonServiceGateway;

import java.util.List;
import java.util.Optional;

public class PersonService implements PersonServiceGateway {

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<PersonEntity> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Optional<PersonEntity> findById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public Optional<PersonEntity> findByName(String name) {
        return personRepository.findByName(name);
    }

    @Override
    public Optional<PersonEntity> findByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    @Override
    public PersonEntity save(PersonEntity personEntity) {
        return personRepository.save(personEntity);
    }

    @Override
    public void delete(Long id) {
        personRepository.deleteById(id);
    }
}
