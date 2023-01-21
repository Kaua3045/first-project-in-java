package com.kaua.first.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    @DisplayName("Should return false if password contain less than 8 characters")
    void shouldReturnFalseIfPasswordInvalid() {
        Person person = new Person(
                "john",
                "john.mail@mail.com",
                "john"
        );

        Assertions.assertFalse(person.passwordIsValid());
    }

    @Test
    @DisplayName("Should return true if password contains equal or more than 8 characters")
    void shouldReturnTrueIfPasswordValid() {
        Person person = new Person(
                "john",
                "john.mail@mail.com",
                "johnpass"
        );

        Assertions.assertTrue(person.passwordIsValid());
    }
}