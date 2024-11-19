package org.example.service;

import org.example.entity.Tweet;
import org.example.entity.User;

import java.sql.SQLException;

public interface UserService {

    User save(User user);
    User update(User user);
    void deleteById(int id) ;
    User findById(int id) ;
    User findByUsername(String username);
    User findByEmail(String email) ;

    User signup(String displayName, String email,
                       String username, String password, String bio) ;
    User loginWithEmail (String email, String password);
    User loginWithUsername (String username, String password);


    User updateDisplayName(User user, String displayName) ;
    User updateBio(User user, String bio) ;
    User updateEmail(User user, String email) ;
    User updateUsername(User user, String username) ;
    User updatePassword(User user, String oldPass, String newPass) ;
}
