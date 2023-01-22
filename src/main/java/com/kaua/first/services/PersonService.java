package com.kaua.first.services;

import com.kaua.first.either.Either;
import com.kaua.first.either.ErrorCustom;
import com.kaua.first.entities.PersonEntity;
import com.kaua.first.exceptions.UserValidationFailedException;
import com.kaua.first.models.Person;
import com.kaua.first.repositories.PersonRepository;
import com.kaua.first.services.interfaces.PersonServiceGateway;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
        return null;
    }

    public Either<UserValidationFailedException, PersonEntity> save1(Person person) throws UserValidationFailedException {
        List<ErrorCustom> errors = person.validate();

        if (!errors.isEmpty()) {
            return Either.left(UserValidationFailedException.with(errors));
        }

        PersonEntity personEntity = PersonEntity
                .builder()
                .name(person.getName())
                .email(person.getEmail())
                .password(new BCryptPasswordEncoder().encode(person.getPassword()))
                .build();

        personRepository.save(personEntity);

        return Either.right(personEntity);
    }

    @Override
    public void delete(Long id) {
        personRepository.deleteById(id);
    }
}
