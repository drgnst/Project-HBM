package com.example.Website.services;

import com.example.Website.entity.Course;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    Course createUpdateCourse(Long courseId, Course course);

    List<Course> findAll();

    Optional<Course> findOne(Long courseId);

    boolean isExists(Long courseId);

    Course partialUpdate(Long courseId, Course course);

    void delete(Long courseId);
}
