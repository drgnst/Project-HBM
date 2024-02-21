package com.example.demo.api;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("user")
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("insert")
    public void insertUser(@Valid @Nonnull @RequestBody User user){
        userService.insertUser(user);

    }
    @GetMapping("getAll")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping(path = "/get/{id}")
    public User getUserById(@PathVariable("id")UUID id) {
        return userService.getUserById(id).orElse(null);
    }
    @DeleteMapping(path = "/delete/{id}")
    public void deleteUserById(@PathVariable("id")UUID id) {
        userService.deleteUserById(id);
    }
    @PutMapping(path = "/update/{id}")
    public void updateUserById(@PathVariable("id")UUID id,@Valid @Nonnull @RequestBody User user) {
        userService.updateUserById(id, user);
    }
}
