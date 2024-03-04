package com.example.Website.services;

import com.example.Website.entity.Course;
import com.example.Website.repository.CourseRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CourseServiceImp implements CourseService{

    private CourseRepository courseRepository;

    public CourseServiceImp(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course createUpdateCourse(Long courseId, Course course) {
        course.setCourseId(courseId);
        return courseRepository.save(course);
    }

    @Override
    public List<Course> findAll() {
        return StreamSupport
                .stream(
                        courseRepository.findAll().spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Course> findOne(Long courseId) {
        return courseRepository.findById(courseId);
    }

    @Override
    public boolean isExists(Long courseId) {
        return courseRepository.existsById(courseId);
    }

    @Override
    public Course partialUpdate(Long courseId, Course course) {
        course.setCourseId(courseId);

        return courseRepository.findById(courseId).map(existingCourse -> {
            Optional.ofNullable(course.getCourseName()).ifPresent(existingCourse::setCourseName);
            return courseRepository.save(existingCourse);
        }).orElseThrow(() -> new RuntimeException("Book does not exist"));
    }

    @Override
    public void delete(Long courseId) {
        courseRepository.deleteById(courseId);

    }
}
