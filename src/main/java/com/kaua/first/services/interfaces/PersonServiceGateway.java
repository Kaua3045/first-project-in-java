package com.kaua.first.services.interfaces;

import com.kaua.first.entities.PersonEntity;

import java.util.List;
import java.util.Optional;

public interface PersonServiceGateway {

    List<PersonEntity> findAll();
    Optional<PersonEntity> findById(Long id);
    Optional<PersonEntity> findByName(String name);
    Optional<PersonEntity> findByEmail(String email);
    PersonEntity save(PersonEntity personEntity);
    void delete(Long id);
}
