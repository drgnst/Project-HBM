package com.example.Website.controller;

import com.example.Website.DTO.CourseDto;
import com.example.Website.entity.Course;
import com.example.Website.mapper.Mapper;
import com.example.Website.services.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CourseController {

    private CourseService courseService;
    private Mapper<Course, CourseDto> courseMapper;

    public CourseController(Mapper<Course, CourseDto> courseMapper, CourseService courseService){
        this.courseMapper = courseMapper;
        this.courseService = courseService;
    }
    @PutMapping(path = "/course/{courseId}")
    public ResponseEntity<CourseDto> createUpdateBook(@PathVariable Long courseId, @RequestBody CourseDto courseDto){
        Course course = courseMapper.mapFrom(courseDto);
        boolean courseExists = courseService.isExists(courseId);
        Course savedCourse = courseService.createUpdateCourse(courseId, course);
        CourseDto savedUpdatedCourseDto  = courseMapper.mapTo(savedCourse);

        if(courseExists){
            return new ResponseEntity(savedUpdatedCourseDto, HttpStatus.OK);
        }else {
            return new ResponseEntity(savedUpdatedCourseDto, HttpStatus.CREATED);
        }
    }
    @PatchMapping(path ="/course/{courseId}")
    public ResponseEntity<CourseDto> partialUpdateBook(
            @PathVariable("courseId") Long courseId,
            @RequestBody CourseDto courseDto
    ){
        if (!courseService.isExists(courseId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Course course = courseMapper.mapFrom(courseDto);
        Course updatedCourse = courseService.partialUpdate(courseId, course);
        return new ResponseEntity<>(
                courseMapper.mapTo(updatedCourse),
                HttpStatus.OK);
    }

    @GetMapping(path = "/course")
    public List<CourseDto> listCourse(){
        List<Course> courses = courseService.findAll();
        return courses.stream()
                .map(courseMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/course/{courseId}")
    public ResponseEntity<CourseDto> getCourse(@PathVariable("courseId") Long courseId){
        Optional<Course> foundCourse = courseService.findOne(courseId);
        return foundCourse.map(course -> {
            CourseDto courseDto = courseMapper.mapTo(course);
            return new ResponseEntity<>(courseDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/course/{courseId}")
    public ResponseEntity deleteCourse(@PathVariable("courseId") Long courseId){
        courseService.delete(courseId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
