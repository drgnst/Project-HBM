package com.example.demo.dao;

import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class UserDataAccess implements UserDAO {

    private final JdbcTemplate jdbcTemplate;

    private static List<User> userList = new ArrayList<>();

    @Autowired
    public UserDataAccess(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertUser(UUID id, User user) {
        userList.add(new User(id, user.getUsername(), user.getEmail(), user.getPassword(), user.getRole()));

        return 1;
    }

    @Override
    public List<User> getALLUser() {
        final String sql = "select * from Person";

        return jdbcTemplate.query(sql, (resultSet, i)->{
            UUID id = UUID.fromString(resultSet.getString("id"));
            String username = (resultSet.getString("username"));
            String email = (resultSet.getString("email"));
            String password = (resultSet.getString("password"));
            String role = (resultSet.getString("role"));

            return new User(id,username,email,password, role);

        });
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        return userList.stream()
                .filter(user ->  user.getID().equals(id))
                .findFirst();
    }

    @Override
    public int deleteUserById(UUID id) {
        Optional<User> user = getUserById(id);
        if (user.isEmpty()) {

            return 0;
        }
        userList.remove(user.get());
        return 1;
    }

    @Override
    public int updateUserById(UUID id, User user) {
        return getUserById(id)
                .map(e -> {
                    int idx = userList.indexOf(e);
                    if(idx>=0){
                        userList.set(idx, new User(id, user.getUsername(), user.getEmail(), user.getPassword(), user.getRole()));
                        return 1;
                    }
                    return 0;
                }).orElse(0);
    }
}
