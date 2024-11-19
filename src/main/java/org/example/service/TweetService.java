package org.example.service;

import org.example.entity.Tweet;
import org.example.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface TweetService {

    Tweet save(Tweet tweet) ;
    Tweet update(Tweet tweet) ;
    Tweet removeParent(Tweet tweet) ;
    void deleteById(int id) ;
    Tweet findById(int id) ;
    List<Tweet> findByUser(User user) ;
    List<Tweet> findRetweets(int parentId) ;
    List<Tweet> findAll() ;

    Tweet postTweet(User user, String text, int retweetFromId) ;
    void viewMyTweets(User user) ;
    void deleteTweet(User user, int tweetId) ;
    void editTweet(User user, int tweetId, String newText) ;
    void viewAllTweets() ;
    void likeTweet(int tweetId) ;
    void dislikeTweet(int tweetId) ;

    Tweet doesUserOwnTweet(User user, int tweetId) ;
}
