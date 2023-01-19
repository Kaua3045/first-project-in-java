package com.kaua.first.exceptions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("User not found");
    }
}
