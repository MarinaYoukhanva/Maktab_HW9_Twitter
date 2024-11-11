package org.example.service.impl;

import org.apache.commons.codec.binary.Hex;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.service.Authentication;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UserServiceImpl {

    UserRepository userRepository;
    final MessageDigest messageDigest = MessageDigest.getInstance("MD5");


    public UserServiceImpl() throws SQLException, NoSuchAlgorithmException {
        userRepository = new UserRepositoryImpl();
    }

    public User signup(String displayName, String email,
                       String username, String password, String bio) throws SQLException {
        User user = null;
        if (userRepository.findByUsername(username) == null) {
            if (userRepository.findByEmail(email) == null) {
                String hashedPassword = hashPassword(password);
                user = new User(0, displayName, email, username, hashedPassword, bio, null);
                userRepository.save(user);
                user = userRepository.findByUsername(username);
            }
        }
        return user;
    }
    public User loginWithEmail (String email, String password) throws SQLException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            if (hashPassword(password).equals(user.getPassword())) {
                Authentication.setLoggedUser(user);
                return user;
            }
        }
        return null;
    }
    public User loginWithUsername (String username, String password) throws SQLException {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (hashPassword(password).equals(user.getPassword())) {
                Authentication.setLoggedUser(user);
                return user;
            }
        }
        return null;
    }

    private String hashPassword(String password) {
        messageDigest.reset();
        messageDigest.update(password.getBytes(StandardCharsets.UTF_8));
        final byte[] resultByte = messageDigest.digest();
        return new String(Hex.encodeHex(resultByte));
    }
}
