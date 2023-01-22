package com.kaua.first.exceptions;

import com.kaua.first.either.ErrorCustom;

import java.util.ArrayList;
import java.util.List;

public class UserValidationFailedException extends Exception {

    public static List<ErrorCustom> errors;

    public UserValidationFailedException() {
        super("User validation failed");
    }

    public static UserValidationFailedException with(List<ErrorCustom> error) {
        errors = new ArrayList<>();
        if (!error.isEmpty()) {
            errors.addAll(error);
        }

        return new UserValidationFailedException();
    }
}


