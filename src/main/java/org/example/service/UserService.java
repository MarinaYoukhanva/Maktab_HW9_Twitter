package org.example.service;

import org.example.entity.Tweet;
import org.example.entity.User;

import java.sql.SQLException;

public interface UserService {

    User save(User user) throws SQLException;
    User update(User user) throws SQLException;
    void deleteById(int id) throws SQLException;
    User findById(int id) throws SQLException;
    User findByUsername(String username);
    User findByEmail(String email) throws SQLException;

    User signup(String displayName, String email,
                       String username, String password, String bio) throws SQLException;
    User loginWithEmail (String email, String password);
    User loginWithUsername (String username, String password);



}
