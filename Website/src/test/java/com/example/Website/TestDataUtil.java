package com.example.Website;

import com.example.Website.DTO.CourseDto;
import com.example.Website.DTO.UserDto;
import com.example.Website.entity.Course;
import com.example.Website.entity.User;

public class TestDataUtil {

    private TestDataUtil() {
    }

    public static User createTestUserA(){
        return User.builder()
                .id(1L)
                .name("Steven Mad")
                .password("adabbdsf")
                .username("steveM")
                .build();
    }

    public static UserDto createTestUserDtoA(){
        return UserDto.builder()
                .id(1L)
                .name("Steven Mad")
                .username("stevenM")
                .password("adabbdsf")
                .build();
    }

    public static User createTestOwnerB(){
        return User.builder()
                .id(2L)
                .name("Tom Jerry")
                .username("TomJ")
                .password("bbebb")
                .build();
    }



    public static Course createTestCourseA(final User user){
        return Course.builder()
                .courseId(4L)
                .courseName("API config")
                .duration("2 hour")
                .user(user)
                .build();
    }

    public static CourseDto createTestCourseDtoA(final UserDto userDto){
        return CourseDto.builder()
                .courseId(4L)
                .courseName("API config")
                .duration("2 hour")
                .owner(userDto)
                .build();
    }
    public static Course createTestMatB(final User user){
        return Course.builder()
                .courseId(5L)
                .courseName("DB 101")
                .duration("3 hour")
                .user(user)
                .build();
    }
    public static Course createTestMatC(final User user){
        return Course.builder()
                .courseId(6L)
                .courseName("BackEnd Solo")
                .duration("10 hour")
                .user(user)
                .build();
    }

    public static User createTestOwnerC() {
        return User.builder()
                .id(3L)
                .name("Apa La")
                .username("ApaL")
                .password("apal")
                .build();
    }
}
