package com.example.Website.services;

import com.example.Website.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);
    List<User> findAll();
    Optional<User> findOne(Long id);

    boolean isExists(Long id);

    User partialUpdate(Long id, User user);

    void delete(Long id);
}
