package com.example.Website.controller;

import com.example.Website.DTO.UserDto;
import com.example.Website.TestDataUtil;
import com.example.Website.entity.User;
import com.example.Website.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.concurrent.ExecutionException;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {
    private UserService userService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public UserControllerIntegrationTest(UserService userService, MockMvc mockMvc) {
        this.userService = userService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }
    @Test
    public  void testThatCreateUserSuccessfullyReturnsHttp201Created() throws Exception{
        User testOwnerA = TestDataUtil.createTestUserA();
        testOwnerA.setId(null);
        String ownerJson = objectMapper.writeValueAsString(testOwnerA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ownerJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateUserSuccessfullyReturnsSavedUser() throws Exception {
        UserDto testOwnerA = TestDataUtil.createTestUserDtoA();
        testOwnerA.setId(null);
        String ownerJson = objectMapper.writeValueAsString(testOwnerA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ownerJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Steven Mad")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.username").value("stevenM")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").value("adabbdsf")
        );

    }
    @Test
    public void testThatListUsersReturnsHttpStatus200() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testThatListUsersReturnsListOfUsers() throws Exception{
        User testUserA = TestDataUtil.createTestUserA();
        userService.save(testUserA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("Steven Mad")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].username").value("stevenM")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].password").value("adabbdsf")
        );
    }

    @Test
    public void testThatGetUserReturnsHttpStatus200WhenUserExist() throws Exception {
        User testUserA = TestDataUtil.createTestUserA();
        userService.save(testUserA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void  testThatGetUserReturnsUserWhenUserExist() throws Exception {
        User testUserA = TestDataUtil.createTestUserA();
        userService.save(testUserA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Steven Mad")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.username").value("stevenM")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").value("adabbdsf")
        );
    }

    @Test
    public void testThatGetUserReturnsHttpStatus404WhenNoUserExists() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateUserReturnsHttpStatus404WhenNoUserExists() throws Exception{
        UserDto testUserDtoA = TestDataUtil.createTestUserDtoA();
        String userDtoJson = objectMapper.writeValueAsString(testUserDtoA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    public void testThatFullUpdateUserReturnsHttpStatus4200WhenUserExists() throws Exception{
        User testUserA = TestDataUtil.createTestUserA();
        User savedOwner = userService.save(testUserA);

        UserDto testUserDtoA = TestDataUtil.createTestUserDtoA();
        String userDtoJson = objectMapper.writeValueAsString(testUserDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/" + savedOwner.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateUpdatesExistingUser() throws Exception{
        User testUserA = TestDataUtil.createTestUserA();
        User savedOwner = userService.save(testUserA);

        User userDto = TestDataUtil.createTestOwnerB();
        userDto.setId(savedOwner.getId());

        String userDtoUpdateJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/users" + savedOwner.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedOwner.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(userDto.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.username").value(userDto.getUsername())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").value(userDto.getPassword())
        );
    }

    @Test public void testThatPartialUpdateExistingUserReturnsHttpStatus200k() throws Exception {
        User testUserA = TestDataUtil.createTestUserA();
        User savedOwner = userService.save(testUserA);

        UserDto testUserDtoA = TestDataUtil.createTestUserDtoA();
        testUserDtoA.setName("UPDATED");
        String userDtoJson = objectMapper.writeValueAsString(testUserDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/users/" + savedOwner.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateExistingUserReturnsUpdatedUser() throws Exception {
        User testUserA = TestDataUtil.createTestUserA();
        User savedOwner = userService.save(testUserA);

        UserDto testUserDtoA = TestDataUtil.createTestUserDtoA();
        testUserDtoA.setName("UPDATED");
        String userDtoJson = objectMapper.writeValueAsString(testUserDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors" +savedOwner.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedOwner.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("UPDATED")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$,username").value(testUserDtoA.getUsername())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").value(testUserDtoA.getPassword())
        );
    }
    @Test
    public void testThatDeleteUserReturnsHttpStatus204ForNonExistingUser() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.delete("users/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
    @Test
    public void testThatDeleteUserReturnsHttpStatus204ForExistingUser() throws Exception{
        User testUserA = TestDataUtil.createTestUserA();
        User savedOwner = userService.save(testUserA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/" + savedOwner.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
