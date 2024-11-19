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


    User updateDisplayName(User user, String displayName) throws SQLException;

    User updateBio(User user, String bio) throws SQLException;

    User updateEmail(User user, String email) throws SQLException;

    User updateUsername(User user, String username) throws SQLException;

    User updatePassword(User user, String oldPass, String newPass) throws SQLException;
}
