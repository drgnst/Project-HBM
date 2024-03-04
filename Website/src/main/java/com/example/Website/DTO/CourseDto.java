package com.example.Website.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDto {

    private Long courseId;
    private String courseName;
    private String duration;

    private UserDto owner;

}
