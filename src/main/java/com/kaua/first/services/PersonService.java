package com.kaua.first.services;

import com.kaua.first.either.Either;
import com.kaua.first.either.ErrorCustom;
import com.kaua.first.entities.PersonEntity;
import com.kaua.first.exceptions.UserNotFoundException;
import com.kaua.first.exceptions.UserValidationFailedException;
import com.kaua.first.models.Course;
import com.kaua.first.models.dtos.AuthenticationInputRequest;
import com.kaua.first.models.dtos.AuthenticationOutput;
import com.kaua.first.models.Person;
import com.kaua.first.repositories.PersonRepository;
import com.kaua.first.security.JwtService;
import com.kaua.first.services.interfaces.PersonServiceGateway;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Optional<Person> findById(Long id) throws UserNotFoundException {
        Optional<PersonEntity> personEntity = personRepository.findById(id);

        if (personEntity.isEmpty()) {
            throw new UserNotFoundException();
        }

        List<Course> courses = personEntity.get().getCourses().stream().map(c -> new Course(
                c.getId(),
                c.getName(),
                c.getDescription()
        )).collect(Collectors.toList());

        Person person = new Person(
                personEntity.get().getId(),
                personEntity.get().getName(),
                personEntity.get().getEmail(),
                personEntity.get().getPassword()
        );

        person.addAllCourse(courses);

        return Optional.of(person);
    }

    @Override
    public Either<UserNotFoundException, PersonEntity> findByName(String name) {
        Optional<PersonEntity> person = personRepository.findByName(name);

        if (person.isEmpty()) {
            return Either.left(new UserNotFoundException());
        }

        return Either.right(person.get());
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
    public Either<UserValidationFailedException, PersonEntity> save(Person person) {
        List<ErrorCustom> errors = person.validate();

        if (!errors.isEmpty()) {
            return Either.left(UserValidationFailedException.with(errors));
        }

        PersonEntity personEntity = new PersonEntity();
        personEntity.setName(person.getName());
        personEntity.setEmail(person.getEmail());
        personEntity.setPassword(passwordEncoder.encode(person.getPassword()));

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

    public void update(PersonEntity person) {
        personRepository.save(person);
    }

    @Override
    public void delete(Long id) {
        personRepository.deleteById(id);
    }
}
