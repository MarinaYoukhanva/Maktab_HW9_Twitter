package org.example.service.impl;

import org.example.entity.Tweet;
import org.example.repository.TweetRepository;
import org.example.repository.impl.TweetRepositoryImpl;
import org.example.service.TweetService;

import java.sql.SQLException;
import java.util.List;

public class TweetServiceImpl implements TweetService {

    TweetRepository tweetRepository;

    public TweetServiceImpl() throws SQLException {
        tweetRepository = new TweetRepositoryImpl();
    }
    @Override
    public void viewAllTweets() throws SQLException {
        List<Tweet> tweets = tweetRepository.findAll();
        for (Tweet tweet : tweets) {
            System.out.println(tweet);
        }
    }
    @Override
    public void likeTweet(int tweetId) throws SQLException {
        Tweet tweet = tweetRepository.findById(tweetId);
        tweet.setLikes(tweet.getLikes() + 1);
        tweetRepository.update(tweet);
    }

    @Override
    public void dislikeTweet(int tweetId) throws SQLException {
        Tweet tweet = tweetRepository.findById(tweetId);
        tweet.setDislikes(tweet.getDislikes() + 1);
        tweetRepository.update(tweet);
    }
}
