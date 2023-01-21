package com.kaua.first.services;

import com.kaua.first.entities.CourseEntity;
import com.kaua.first.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository _courseRepository;

    public CourseEntity save(CourseEntity course) {
        return _courseRepository.save(course);
    }

    public List<CourseEntity> findAll() {
        return _courseRepository.findAll();
    }

    public Optional<CourseEntity> findById(Long id) {
        return _courseRepository.findById(id);
    }
}
