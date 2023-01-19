package com.kaua.first.exceptions;

public class PasswordInvalidException extends Exception {

    public PasswordInvalidException() {
        super("Password must contain 8 characters at least");
    }
}
