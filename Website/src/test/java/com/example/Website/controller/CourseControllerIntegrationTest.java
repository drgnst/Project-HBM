package com.example.Website.controller;

import com.example.Website.DTO.CourseDto;
import com.example.Website.TestDataUtil;
import com.example.Website.entity.Course;
import com.example.Website.entity.User;
import com.example.Website.services.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class CourseControllerIntegrationTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private CourseService courseService;

    public CourseControllerIntegrationTest(MockMvc mockMvc, CourseService courseService) {
        this.mockMvc = mockMvc;
        this.objectMapper =  new ObjectMapper();
        this.courseService = courseService;
    }

    @Test
    public void testThatCreateCourseReturnsHttpStatus201Created() throws Exception{
        CourseDto testMatA = TestDataUtil.createTestCourseDtoA(null);
        String courseJson = objectMapper.writeValueAsString(testMatA);

        mockMvc.perform(MockMvcRequestBuilders.put("/course/" + 4L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(courseJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatUpdateBookReturnsHttpStatus200Ok() throws Exception{
        Course testCourseA = TestDataUtil.createTestCourseA(null);
        Course savedCourse = courseService.createUpdateCourse(
                testCourseA.getCourseId(), testCourseA
        );

        CourseDto testMatA = TestDataUtil.createTestCourseDtoA(null);
        testMatA.setCourseId(savedCourse.getCourseId());
        String courseJson = objectMapper.writeValueAsString(testMatA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/course/" + savedCourse.getCourseId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatCreateCourseReturnsCreatedBook() throws Exception {
        CourseDto testMatA = TestDataUtil.createTestCourseDtoA(null);
        String courseJson = objectMapper.writeValueAsString(testMatA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/course/255")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.courseId").value(4L)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("API config")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.duration").value("2 hour")
        );
    }

    @Test
    public void testThatUpdateCourseReturnsUpdateCourse() throws Exception{
        Course testCourseA =TestDataUtil.createTestCourseA(null);
        Course savedCourse = courseService.createUpdateCourse(
                testCourseA.getCourseId(), testCourseA
        );

        CourseDto testMatA = TestDataUtil.createTestCourseDtoA(null);
        testMatA.setCourseId(savedCourse.getCourseId());
        testMatA.setCourseName("UPDATED");
        String courseJson = objectMapper.writeValueAsString(testMatA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/course/" + savedCourse.getCourseId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.courseId").value(4L)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.courseName").value("UPDATED")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.duration").value("2 hour")
        );

    }
    @Test
    public void testThatListCourseReturnsHttpStatus200Ok() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/course")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatListCourseReturnsCourse() throws Exception{
        Course testCourseA = TestDataUtil.createTestCourseA(null);
        courseService.createUpdateCourse(testCourseA.getCourseId(), testCourseA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/course")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].courseId").value(4L)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].courseName").value("API config")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].duration").value("2 hour")
        );
    }
    @Test
    public void testThatGetCourseReturnsHttpStatus200OkWhenCourseExists() throws Exception{
        Course testCourseA = TestDataUtil.createTestCourseA(null);
        courseService.createUpdateCourse(testCourseA.getCourseId(), testCourseA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/course/" + testCourseA.getCourseId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetCourseReturnsHttpStatus404WhenBookDoesntExist() throws Exception{
        Course testCourseA = TestDataUtil.createTestCourseA(null);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/course/" + testCourseA.getCourseId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatPartialUpdateCourseReturnsHttpStatus200Ok() throws Exception{
        Course testCourseA = TestDataUtil.createTestCourseA(null);
        courseService.createUpdateCourse(testCourseA.getCourseId(), testCourseA);

        CourseDto testMatA = TestDataUtil.createTestCourseDtoA(null);
        testMatA.setCourseName("UPDATED");
        String courseJson = objectMapper.writeValueAsString(testMatA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/course/" + testCourseA.getCourseId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatPartialUpdateCourseReturnsUpdatedCourse() throws Exception{
        Course testCourseA = TestDataUtil.createTestCourseA(null);
        courseService.createUpdateCourse(testCourseA.getCourseId(), testCourseA);

        CourseDto testMatA = TestDataUtil.createTestCourseDtoA(null);
        testMatA.setCourseName("UPDATED");
        String courseJson = objectMapper.writeValueAsString(testMatA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/course/" + testCourseA.getCourseId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.courseId").value(testCourseA.getCourseId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.courseName").value("UPDATED")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.duration").value(testCourseA.getDuration())
        );
    }
    @Test
    public void testThatDeleteNonExistingCourseReturnsHttpStatus204NoContent() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/course/dadadadda")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
    @Test
    public void testThatDeleteExistingCourseReturnsHttpStatus204NoContent() throws Exception{
        Course testCourseA = TestDataUtil.createTestCourseA(null);
        courseService.createUpdateCourse(testCourseA.getCourseId(), testCourseA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/course/" + testCourseA.getCourseId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
