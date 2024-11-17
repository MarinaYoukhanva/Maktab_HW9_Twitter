package org.example.service.impl;

import org.apache.commons.codec.binary.Hex;
import org.example.entity.*;
import org.example.exception.IncorrectPasswordException;
import org.example.exception.UserNotFoundException;
import org.example.service.*;
import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    TweetService tweetService;
    TagService tagService;
    TweetTagService tweetTagService;
    final MessageDigest messageDigest = MessageDigest.getInstance("MD5");


    public UserServiceImpl() throws SQLException, NoSuchAlgorithmException {
        userRepository = new UserRepositoryImpl();
        tweetService = new TweetServiceImpl();
        tagService = new TagServiceImpl();
        tweetTagService = new TweetTagServiceImpl();
    }

    @Override
    public User save(User user) throws SQLException {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) throws SQLException {
        return userRepository.update(user);
    }

    @Override
    public void deleteById(int id) throws SQLException {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(int id) throws SQLException {
        return userRepository.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User signup(String displayName, String email,
                       String username, String password, String bio) throws SQLException {
        User user = null;
        if (findByUsername(username) != null)
            throw new RuntimeException("username already exists");
        if (findByEmail(email) == null) {
            if (email.endsWith("@gmail.com")) {
                String hashedPassword = hashPassword(password);
                user = new User(0, displayName, email, username, hashedPassword, bio, null);
                save(user);
                user = findByUsername(username);
            } else System.out.println("gmail format error! ");
        }

        return user;
    }

    @Override
    public User loginWithEmail(String email, String password) {
        User user = findByEmail(email);
        if (!hashPassword(password).equals(user.getPassword()))
            throw new IncorrectPasswordException();
        Authentication.setLoggedUser(user);
        return user;
}

@Override
public User loginWithUsername(String username, String password) {
    User user = findByUsername(username);
    if (!hashPassword(password).equals(user.getPassword()))
        throw new IncorrectPasswordException();
    Authentication.setLoggedUser(user);
    return user;
}

private String hashPassword(String password) {
    messageDigest.reset();
    messageDigest.update(password.getBytes(StandardCharsets.UTF_8));
    final byte[] resultByte = messageDigest.digest();
    return new String(Hex.encodeHex(resultByte));
}

}
