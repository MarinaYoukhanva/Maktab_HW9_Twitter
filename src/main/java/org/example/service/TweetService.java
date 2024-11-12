package org.example.service;

import java.sql.SQLException;

public interface TweetService {

    void viewAllTweets() throws SQLException;

    void likeTweet(int tweetId) throws SQLException;
    void dislikeTweet(int tweetId) throws SQLException;
}
