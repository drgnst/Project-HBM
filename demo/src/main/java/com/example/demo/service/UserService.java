package com.example.demo.service;

import com.example.demo.dao.UserDAO;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserDAO userDAO;

    @Autowired
    public UserService(@Qualifier("postgres") UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public int insertUser(User user){
        return userDAO.insertUser(user);

    }
    public List<User> getAllUser(){
        return userDAO.getALLUser();
    }

    public Optional<User> getUserById(UUID id){
        return userDAO.getUserById(id);
    }

    public int deleteUserById(UUID id){
        return userDAO.deleteUserById(id);
    }
    public int updateUserById(UUID id, User user){
        return userDAO.updateUserById(id, user);
    }

}
