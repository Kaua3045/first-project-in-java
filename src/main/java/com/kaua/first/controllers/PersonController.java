package com.kaua.first.controllers;

import com.kaua.first.either.Either;
import com.kaua.first.entities.PersonEntity;
import com.kaua.first.exceptions.EmailAlreadyExistsException;
import com.kaua.first.exceptions.UserNotFoundException;
import com.kaua.first.exceptions.UserValidationFailedException;
import com.kaua.first.models.dtos.AuthenticationInputRequest;
import com.kaua.first.models.dtos.AuthenticationOutput;
import com.kaua.first.models.Person;
import com.kaua.first.services.PersonService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/person")
public class PersonController {

    @Autowired
    private PersonService _personService;

    @GetMapping("/")
    public List<PersonEntity> listAll() {
        return _personService.findAll();
    }

    @GetMapping("/id/{id}")
    @Transactional
    public ResponseEntity<PersonEntity> getById(@PathVariable Long id) throws UserNotFoundException {
        Optional<PersonEntity> person = _personService.findById(id);

        if (person.isEmpty()) {
            throw new UserNotFoundException();
        }

        return ResponseEntity.ok(person.get());
    }

    @GetMapping("/{name}")
    public ResponseEntity<PersonEntity> getByName(@PathVariable String name) throws UserNotFoundException {
        Either<UserNotFoundException, PersonEntity> person = _personService.findByName(name);

        if (person.isLeft()) {
            throw person.getLeft();
        }

        return ResponseEntity.ok(person.getRight());
    }

    @PostMapping("/create")
    public ResponseEntity<PersonEntity> create(@RequestBody Person person) throws Throwable {
        Optional<PersonEntity> emailExists = _personService.findByEmail(person.getEmail());

        if (emailExists.isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        Either<UserValidationFailedException, PersonEntity> result = _personService.save(person);

        if (result.isLeft()) {
            throw result.getLeft();
        }

        return ResponseEntity.ok(result.getRight());
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthenticationOutput> login(@RequestBody AuthenticationInputRequest request) throws UserNotFoundException {
        return ResponseEntity.ok(_personService.authenticate(request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) throws UserNotFoundException {
        Optional<PersonEntity> person = _personService.findById(id);

        if (person.isEmpty()) {
            throw new UserNotFoundException();
        }

        _personService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
