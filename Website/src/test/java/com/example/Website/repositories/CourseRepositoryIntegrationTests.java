package com.example.Website.repositories;

import com.example.Website.TestDataUtil;
import com.example.Website.entity.Course;
import com.example.Website.entity.User;
import com.example.Website.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CourseRepositoryIntegrationTests {

    private CourseRepository underTest;

    @Autowired
    public CourseRepositoryIntegrationTests(CourseRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatCourseCanBeCreatedAndRecalled(){
        User user = TestDataUtil.createTestUserA();
        Course course = TestDataUtil.createTestCourseA(user);
        underTest.save(course);
        Optional<Course> result = underTest.findById(course.getCourseId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(course);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled(){
        User user = TestDataUtil.createTestUserA();

        Course courseA = TestDataUtil.createTestCourseA(user);
        underTest.save(courseA);

        Course courseB = TestDataUtil.createTestMatB(user);
        underTest.save(courseB);

        Course courseC = TestDataUtil.createTestMatC(user);
        underTest.save(courseC);

        Iterable<Course> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(courseA, courseB, courseC);

    }
    @Test
    public void testThatCourseCanBeUpdated() {
        User user = TestDataUtil.createTestUserA();

        Course courseA = TestDataUtil.createTestCourseA(user);
        underTest.save(courseA);

        courseA.setCourseName("UPDATED");
        underTest.save(courseA);

        Optional<Course> result = underTest.findById(courseA.getCourseId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(courseA);

    }

    @Test
    public void testThatBookCanBeDeleted(){
        User user = TestDataUtil.createTestUserA();

        Course courseA = TestDataUtil.createTestCourseA(user);
        underTest.save(courseA);

        underTest.deleteById(courseA.getCourseId());

        Optional<Course> result = underTest.findById(courseA.getCourseId());
        assertThat(result).isEmpty();
    }
}
