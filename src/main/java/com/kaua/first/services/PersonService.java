package com.kaua.first.services;

import com.kaua.first.either.Either;
import com.kaua.first.either.ErrorCustom;
import com.kaua.first.entities.PersonEntity;
import com.kaua.first.exceptions.UserNotFoundException;
import com.kaua.first.exceptions.UserValidationFailedException;
import com.kaua.first.models.AuthenticationInputRequest;
import com.kaua.first.models.AuthenticationOutput;
import com.kaua.first.models.Person;
import com.kaua.first.repositories.PersonRepository;
import com.kaua.first.security.JwtService;
import com.kaua.first.services.interfaces.PersonServiceGateway;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

public class PersonService implements PersonServiceGateway {

    private PersonRepository personRepository;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;

    public PersonService(PersonRepository personRepository, AuthenticationManager authenticationManager, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
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
        Optional<PersonEntity> person = personRepository.findByEmail(email);

        if (person.isEmpty()) {
            return person;
        }

        return Optional.of(person.get());
    }

    @Override
    public PersonEntity save(PersonEntity personEntity) {
        return null;
    }

    public Either<UserValidationFailedException, PersonEntity> save1(Person person) {
        List<ErrorCustom> errors = person.validate();

        if (!errors.isEmpty()) {
            return Either.left(UserValidationFailedException.with(errors));
        }

        PersonEntity personEntity = PersonEntity
                .builder()
                .name(person.getName())
                .email(person.getEmail())
                .password(passwordEncoder.encode(person.getPassword()))
                .build();

        personRepository.save(personEntity);

        return Either.right(personEntity);
    }

    public AuthenticationOutput authenticate(AuthenticationInputRequest request) throws UserNotFoundException {
        Optional<PersonEntity> person = personRepository.findByEmail(request.email());

        if (person.isEmpty()) {
            throw new UserNotFoundException();
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        ));

        String jwtToken = jwtService.generateToken(person.get());

        return new AuthenticationOutput(jwtToken);
    }

    @Override
    public void delete(Long id) {
        personRepository.deleteById(id);
    }
}
