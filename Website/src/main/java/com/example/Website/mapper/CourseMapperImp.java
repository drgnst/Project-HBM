package com.example.Website.mapper;

import com.example.Website.DTO.CourseDto;
import com.example.Website.entity.Course;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CourseMapperImp implements Mapper<Course, CourseDto> {

    private ModelMapper modelMapper;

    public CourseMapperImp(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public CourseDto mapTo(Course course) {
        return modelMapper.map(course, CourseDto.class);
    }

    @Override
    public Course mapFrom(CourseDto courseDto) {
        return modelMapper.map(courseDto, Course.class);
    }
}
