package com.kaua.first.services.interfaces;

import com.kaua.first.either.Either;
import com.kaua.first.entities.PersonEntity;
import com.kaua.first.exceptions.UserNotFoundException;
import com.kaua.first.exceptions.UserValidationFailedException;
import com.kaua.first.models.Person;

import java.util.List;
import java.util.Optional;

public interface PersonServiceGateway {

    List<PersonEntity> findAll();
    Optional<PersonEntity> findById(Long id);
    Either<UserNotFoundException, PersonEntity> findByName(String name);
    Optional<PersonEntity> findByEmail(String email);
    Either<UserValidationFailedException, PersonEntity> save(Person person);
    void delete(Long id);
}
