package com.kaua.first.services;

import com.kaua.first.entities.PersonEntity;
import com.kaua.first.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<PersonEntity> findAll() {
        return personRepository.findAll();
    }

    public Optional<PersonEntity> findById(Long id) {
        Optional<PersonEntity> person = personRepository.findById(id);
        if (person.isEmpty()) {
            return null;
        }
        return person;
    }

    public PersonEntity save(PersonEntity personEntity) {
        return personRepository.save(personEntity);
    }

    public void delete(Long id) {
        personRepository.deleteById(id);
    }
}
