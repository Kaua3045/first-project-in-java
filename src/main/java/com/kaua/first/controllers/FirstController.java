package com.kaua.first.controllers;

import com.kaua.first.AppError;
import com.kaua.first.entities.PersonEntity;
import com.kaua.first.models.Person;
import com.kaua.first.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class FirstController {

    @Autowired
    private PersonService personService;

    @GetMapping("/")
    public List<PersonEntity> listAll() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Optional<PersonEntity> person = personService.findById(id);

        if (person.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AppError(
                            new Exception("User not found").getMessage()
                    ));
        }

        return ResponseEntity.ok(person);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody Person person) {
        if (!person.passwordIsValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AppError(
                            new Exception("Password must contain 8 characters at least").getMessage()
                    ));
        }

        PersonEntity personEntity = PersonEntity.builder()
                .name(person.getName())
                .email(person.getEmail())
                .password(person.getPassword())
                .build();

        personService.save(personEntity);

        return ResponseEntity.ok(personEntity);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        Optional<PersonEntity> person = personService.findById(id);

        if (person.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AppError(
                            new Exception("User not found").getMessage()
                    ));
        }

        personService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(null);
    }
}
