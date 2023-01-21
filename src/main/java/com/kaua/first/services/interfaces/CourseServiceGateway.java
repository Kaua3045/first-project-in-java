package com.kaua.first.services.interfaces;

import com.kaua.first.entities.CourseEntity;

import java.util.List;
import java.util.Optional;

public interface CourseServiceGateway {

    List<CourseEntity> findAll();
    Optional<CourseEntity> findById(Long id);
    CourseEntity save(CourseEntity course);
}
