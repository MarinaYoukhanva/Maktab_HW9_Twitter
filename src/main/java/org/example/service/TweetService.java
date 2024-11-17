package org.example.service;

import org.example.entity.Tweet;
import org.example.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface TweetService {

    Tweet save(Tweet tweet) throws SQLException;
    Tweet update(Tweet tweet) throws SQLException;
    void deleteById(int id) throws SQLException;
    Tweet findById(int id) throws SQLException;
    List<Tweet> findByUser(User user) throws SQLException;
    List<Tweet> findAll() throws SQLException;

    void viewAllTweets() throws SQLException;
    void likeTweet(int tweetId) throws SQLException;
    void dislikeTweet(int tweetId) throws SQLException;
}
