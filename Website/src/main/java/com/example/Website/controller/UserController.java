package com.example.Website.controller;

import com.example.Website.DTO.UserDto;
import com.example.Website.entity.User;
import com.example.Website.mapper.Mapper;
import com.example.Website.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {
    private UserService userService;

    private Mapper<User, UserDto> userMapper;

    public UserController(UserService userService, Mapper<User, UserDto> userMapper){
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping(path = "/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto owner){
        User user = userMapper.mapFrom(owner);
        User savedUser = userService.save(user);
        return new ResponseEntity<>(userMapper.mapTo(savedUser), HttpStatus.CREATED);
    }

    @GetMapping(path = "/users")
    public List<UserDto> listUsers(){
        List<User> owners = userService.findAll();
        return owners.stream()
                .map(userMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id){
        Optional<User> foundUser = userService.findOne(id);
        return foundUser.map(user -> {
            UserDto userDto = userMapper.mapTo(user);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> fullUpdateUser(
            @PathVariable("id") Long id,
            @RequestBody UserDto userDto){
        if (!userService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userDto.setId(id);
        User user = userMapper.mapFrom(userDto);
        User savedUser = userService.save(user);
        return new ResponseEntity<>(
                userMapper.mapTo(savedUser),
                HttpStatus.OK
        );
    }
    @PatchMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> partialUpdate(
            @PathVariable("id") Long id,
            @RequestBody UserDto userDto
    ){
        if (!userService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = userMapper.mapFrom(userDto);
        User updatedUser = userService.partialUpdate(id, user);
        return new ResponseEntity<>(
                userMapper.mapTo(updatedUser),
                HttpStatus.OK
        );
    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id){
        userService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
