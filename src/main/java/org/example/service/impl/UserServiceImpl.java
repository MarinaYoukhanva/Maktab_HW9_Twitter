package org.example.service.impl;

import org.apache.commons.codec.binary.Hex;
import org.example.entity.*;
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
    public User findByUsername(String username) throws SQLException {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) throws SQLException {
        return userRepository.findByEmail(email);
    }

    @Override
    public User signup(String displayName, String email,
                       String username, String password, String bio) throws SQLException {
        User user = null;
        if (userRepository.findByUsername(username) == null) {
            if (userRepository.findByEmail(email) == null) {
                if (email.endsWith("@gmail.com")) {
                    String hashedPassword = hashPassword(password);
                    user = new User(0, displayName, email, username, hashedPassword, bio, null);
                    userRepository.save(user);
                    user = userRepository.findByUsername(username);
                } else System.out.println("gmail format error! ");
            }
        }
        return user;
    }

    @Override
    public User loginWithEmail(String email, String password) throws SQLException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            if (hashPassword(password).equals(user.getPassword())) {
                Authentication.setLoggedUser(user);
                return user;
            }
        }
        return null;
    }

    @Override
    public User loginWithUsername(String username, String password) throws SQLException {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (hashPassword(password).equals(user.getPassword())) {
                Authentication.setLoggedUser(user);
                return user;
            }
        }
        return null;
    }

    @Override
    public Tweet postTweet(User user, String text, int retweetFromId) throws SQLException {
        Tweet retweetFrom = tweetService.findById(retweetFromId);
        Tweet tweet = new Tweet(0, text, 0, 0, user, retweetFrom);
        return tweetService.save(tweet);
    }

    @Override
    public boolean viewMyTweets(User user) throws SQLException {
        List<Tweet> tweets = tweetService.findByUser(user);
        if (!tweets.isEmpty()) {
            for (Tweet tweet : tweets) {
                System.out.println(tweet);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteTweet(User user, int tweetId) throws SQLException {
        List<Tweet> tweets = tweetService.findByUser(user);
        Tweet tweet = tweetService.findById(tweetId);
        if (tweets.contains(tweet)) {
            tweetService.deleteById(tweetId);
            return true;
        }
        return false;
    }

    @Override
    public boolean editTweet(User user, int tweetId, String newText) throws SQLException {
        Tweet tweet = doesUserOwnTweet(user, tweetId);
        if (tweet != null) {
            tweet.setText(newText);
            tweetService.update(tweet);
            return true;
        }
        return false;
    }

    @Override
    public void chooseTag(User user, int tweetId, int tagId) throws SQLException {
        Tweet tweet = doesUserOwnTweet(user, tweetId);
        if (tweet != null) {
            Tag tag = hasTweetTheTag(tweetId, tagId);
            if (tag != null) {
                tweetTagService.save(tweet, tag);
                tweet.getTags().add(tag);
            }
        }
    }

    @Override
    public void deleteTagForTweet(User user, int tweetId, int tagId) throws SQLException {
        Tweet tweet = doesUserOwnTweet(user, tweetId);
        if (tweet != null) {
            Tag tag = hasTweetTheTag(tweetId, tagId);
            if (tag != null) {
                tweetTagService.deleteById(tweetId, tagId);
                tweet.getTags().remove(tag);
            }
        }
    }


    private String hashPassword(String password) {
        messageDigest.reset();
        messageDigest.update(password.getBytes(StandardCharsets.UTF_8));
        final byte[] resultByte = messageDigest.digest();
        return new String(Hex.encodeHex(resultByte));
    }

    private Tweet doesUserOwnTweet(User user, int tweetId) throws SQLException {
        List<Tweet> tweets = tweetService.findByUser(user);
        Tweet tweet = tweetService.findById(tweetId);
        if (tweets.contains(tweet))
            return tweet;
        return null;
    }

    private Tag hasTweetTheTag(int tweetId, int tagId) throws SQLException {
        List<Tag> tags = tweetTagService.findTagsForTweet(tweetId);
        Tag tag = tagService.findById(tagId);
        if (tags.contains(tag))
            return tag;
        return null;
    }
}
