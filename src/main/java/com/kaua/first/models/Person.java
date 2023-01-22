package com.kaua.first.models;

import com.kaua.first.either.ErrorCustom;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Person {

    private String name;
    private String email;
    private String password;

    public List<ErrorCustom> validate() {
        List<ErrorCustom> errors = new ArrayList<>();
        if (!passwordIsValid()) {
            errors.add(new ErrorCustom("Password must contain 8 characters at least"));
        }

        return errors;
    }

    public boolean passwordIsValid() {
        if (this.password.length() < 8) {
            return false;
        }
        return true;
    }
}
