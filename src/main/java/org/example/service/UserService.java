package org.example.service;

import org.example.entity.Tweet;
import org.example.entity.User;

import java.sql.SQLException;

public interface UserService {
    User signup(String displayName, String email,
                       String username, String password, String bio) throws SQLException;
    User loginWithEmail (String email, String password) throws SQLException;
    User loginWithUsername (String username, String password) throws SQLException;
    Tweet postTweet(User user, String text, Tweet retweetFrom) throws SQLException;


    boolean viewMyTweets(User user) throws SQLException;

    boolean deleteTweet(User user, int tweetId) throws SQLException;
    void editTweet () throws SQLException;
    void viewTweets () throws SQLException;


}
