package com.kaua.first.repositories;

import com.kaua.first.entities.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    Optional<PersonEntity> findByName(String name);
    Optional<PersonEntity> findByEmail(String email);
}
