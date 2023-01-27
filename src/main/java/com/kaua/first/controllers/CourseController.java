package com.kaua.first.controllers;

import com.kaua.first.entities.CourseEntity;
import com.kaua.first.entities.PersonEntity;
import com.kaua.first.exceptions.CourseNotFoundException;
import com.kaua.first.exceptions.UserNotFoundException;
import com.kaua.first.models.Course;
import com.kaua.first.models.Person;
import com.kaua.first.services.CourseService;
import com.kaua.first.services.PersonService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/course")
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
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setName(course.getName());
        courseEntity.setDescription(course.getDescription());

        _courseService.save(courseEntity);

        return ResponseEntity.ok(courseEntity);
    }

    @PostMapping("/buy/{courseId}/{personId}")
    @Transactional
    public ResponseEntity<Object> buyCourse(
            @PathVariable Long courseId,
            @PathVariable Long personId
    ) throws CourseNotFoundException, UserNotFoundException {
        Optional<CourseEntity> courseEntity = _courseService.findById(courseId);

        if (courseEntity.isEmpty()) {
            throw new CourseNotFoundException();
        }

        Optional<Person> person = _personService.findById(personId);

        if (person.isEmpty()) {
            throw new UserNotFoundException();
        }

        Course c = new Course(
                courseEntity.get().getId(),
                courseEntity.get().getName(),
                courseEntity.get().getDescription()
        );

        person.get().addCourse(c);

        List<CourseEntity> courseEntities = person.get().getCourses().stream().map(c2 -> new CourseEntity(
                c2.getId(),
                c2.getName(),
                c2.getDescription())
        ).collect(Collectors.toList());

        PersonEntity personEntity = new PersonEntity(
                person.get().getId(),
                person.get().getName(),
                person.get().getEmail(),
                person.get().getPassword()
        );

        personEntity.addAllCourses(courseEntities);
        _personService.update(personEntity);

        return ResponseEntity.noContent().build();
    }
}
