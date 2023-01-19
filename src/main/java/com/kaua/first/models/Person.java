package com.kaua.first.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person {

    private String name;
    private String email;
    private String password;

    public boolean passwordIsValid() {
        if (this.password.length() < 8) {
            return false;
        }
        return true;
    }
}
