package org.example.service;

import org.example.entity.Tweet;
import org.example.entity.User;

import java.sql.SQLException;

public interface UserService {

    User save(User user) throws SQLException;
    User update(User user) throws SQLException;
    void deleteById(int id) throws SQLException;
    User findById(int id) throws SQLException;
    User findByUsername(String username) throws SQLException;
    User findByEmail(String email) throws SQLException;

    User signup(String displayName, String email,
                       String username, String password, String bio) throws SQLException;
    User loginWithEmail (String email, String password) throws SQLException;
    User loginWithUsername (String username, String password) throws SQLException;
    Tweet postTweet(User user, String text, int retweetFromId) throws SQLException;
    boolean viewMyTweets(User user) throws SQLException;
    boolean deleteTweet(User user, int tweetId) throws SQLException;
    boolean editTweet(User user, int tweetId, String newText) throws SQLException;
    void chooseTag(User user, int tweetId, int TagId) throws SQLException;
    void deleteTagForTweet(User user, int tweetId, int tagId) throws SQLException;
}
