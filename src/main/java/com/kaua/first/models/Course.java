package com.kaua.first.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Course {

    private Long id;
    private String name;
    private String description;

    @JsonIgnore
    private List<Person> persons;

    public Course(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.persons = new ArrayList<>();
    }
}
