package com.example.demo.dao;

import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UserDAO {

    int insertUser(UUID id, User user);

    default  int insertUser(User user){
        UUID id = UUID.randomUUID();
        return insertUser(id, user);
    }

    List<User> getALLUser();
    Optional<User> getUserById(UUID id);

    int deleteUserById (UUID id);
    int updateUserById (UUID id, User user);
}
