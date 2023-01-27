package com.kaua.first.models;

import com.kaua.first.either.ErrorCustom;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Person {

    private Long id;
    private String name;
    private String email;
    private String password;
    private List<Course> courses;

    public Person(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.courses = new ArrayList<>();
    }

    public void addAllCourse(List<Course> course) {
        this.courses.addAll(course);
        course.forEach(x -> x.getPersons().add(this));
    }

    public void addCourse(Course course) {
        this.courses.add(course);
        course.getPersons().add(this);
    }

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
