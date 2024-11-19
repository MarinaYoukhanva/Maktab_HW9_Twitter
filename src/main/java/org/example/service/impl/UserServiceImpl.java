package org.example.service.impl;

import org.apache.commons.codec.binary.Hex;
import org.example.entity.*;
import org.example.exception.*;
import org.example.service.*;
import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

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
        User user = findByUsername(username);
        if (user != null)
            throw new TakenUsernameException();
        if (findByEmail(email) != null)
            throw new TakenEmailException();
        String hashedPassword = hashPassword(password);
        user = new User(0, displayName, email, username, hashedPassword, bio, null);
        save(user);
        return user;
    }

    @Override
    public User loginWithEmail(String email, String password) {
        User user;
        try {
            user = findByEmail(email);
            if (!hashPassword(password).equals(user.getPassword()))
                throw new IncorrectPasswordException();
        } catch (NullPointerException e) {
            throw new UserNotFoundException();
        }
        Authentication.setLoggedUser(user);
        return user;
    }

    @Override
    public User loginWithUsername(String username, String password) {
        User user;
        try {
            user = findByUsername(username);
            if (!hashPassword(password).equals(user.getPassword()))
                throw new IncorrectPasswordException();
        } catch (NullPointerException e) {
            throw new UserNotFoundException();
        }
        Authentication.setLoggedUser(user);
        return user;
    }

    @Override
    public User updateDisplayName(User user, String displayName) throws SQLException {
        user.setDisplayName(displayName);
        return update(user);
    }

    @Override
    public User updateBio(User user, String bio) throws SQLException {
        user.setDisplayName(bio);
        return update(user);
    }

    @Override
    public User updateEmail(User user, String email) throws SQLException {
        User doesExist = findByEmail(email);
        if (doesExist != null)
            throw new TakenEmailException();
        user.setEmail(email);
        return update(user);
    }

    @Override
    public User updateUsername(User user, String username) throws SQLException {
        User doesExist = findByUsername(username);
        if (doesExist != null)
            throw new TakenUsernameException();
        user.setUsername(username);
        return update(user);
    }

    @Override
    public User updatePassword(User user, String oldPass, String newPass) throws SQLException {
        if (!hashPassword(oldPass).equals(user.getPassword()))
            throw new IncorrectPasswordException();
        user.setPassword(hashPassword(newPass));
        return update(user);
    }

    private String hashPassword(String password) {
        messageDigest.reset();
        messageDigest.update(password.getBytes(StandardCharsets.UTF_8));
        final byte[] resultByte = messageDigest.digest();
        return new String(Hex.encodeHex(resultByte));
    }

}
