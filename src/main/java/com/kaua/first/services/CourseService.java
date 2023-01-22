package com.kaua.first.services;

import com.kaua.first.entities.CourseEntity;
import com.kaua.first.repositories.CourseRepository;
import com.kaua.first.services.interfaces.CourseServiceGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class CourseService implements CourseServiceGateway {

    private CourseRepository _courseRepository;

    public CourseService(CourseRepository _courseRepository) {
        this._courseRepository = _courseRepository;
    }

    @Override
    public CourseEntity save(CourseEntity course) {
        return _courseRepository.save(course);
    }

    @Override
    public List<CourseEntity> findAll() {
        return _courseRepository.findAll();
    }

    @Override
    public Optional<CourseEntity> findById(Long id) {
        return _courseRepository.findById(id);
    }
}
