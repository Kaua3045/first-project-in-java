package com.kaua.first.configuration.course;

import com.kaua.first.repositories.CourseRepository;
import com.kaua.first.services.CourseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CourseConfiguration {

    @Bean
    public CourseService courseServiceBean(CourseRepository courseRepository) {
        return new CourseService(courseRepository);
    }
}
