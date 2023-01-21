package com.kaua.first.exceptions;

public class CourseNotFoundException extends Exception {

    public CourseNotFoundException() {
        super("Course not found");
    }
}
