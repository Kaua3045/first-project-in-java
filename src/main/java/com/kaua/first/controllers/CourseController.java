package com.kaua.first.controllers;

import com.kaua.first.entities.CourseEntity;
import com.kaua.first.entities.PersonEntity;
import com.kaua.first.models.Course;
import com.kaua.first.services.CourseService;
import com.kaua.first.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService _courseService;

    @Autowired
    private PersonService _personService;

    @GetMapping
    public List<CourseEntity> getAll() {
        return _courseService.findAll();
    }

    @PostMapping
    public ResponseEntity<CourseEntity> createCourse(@RequestBody Course course) {
        CourseEntity courseEntity = CourseEntity.builder()
                .name(course.getName())
                .description(course.getDescription())
                .build();

        _courseService.save(courseEntity);

        return ResponseEntity.ok(courseEntity);
    }

    @PostMapping("/buy/{courseId}/{personId}")
    public ResponseEntity<Object> buyCourse(
            @PathVariable Long courseId,
            @PathVariable Long personId
    ) {
        Optional<CourseEntity> courseEntity = _courseService.findById(courseId);

        if (courseEntity.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<PersonEntity> personEntity = _personService.findById(personId);

        if (personEntity.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        personEntity.get().addCourse(courseEntity.get());
        PersonEntity result = _personService.save(personEntity.get());

        return ResponseEntity.ok(result);
    }
}
