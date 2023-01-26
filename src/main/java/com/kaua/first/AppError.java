package com.kaua.first;

import com.kaua.first.either.ErrorCustom;
import lombok.Data;

import java.util.List;

@Data
public class AppError {

    private String message;

    private List<ErrorCustom> validations;

    public AppError(String message) {
        this.message = message;
    }

    public AppError(String message, List<ErrorCustom> validations) {
        this.message = message;
        this.validations = validations;
    }
}
