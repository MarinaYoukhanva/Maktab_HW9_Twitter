package org.example.service.impl;

import org.example.entity.Tweet;
import org.example.entity.User;
import org.example.exception.UserDoesNotOwnTweetException;
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
    public Tweet save(Tweet tweet) throws SQLException {
        return tweetRepository.save(tweet);
    }

    @Override
    public Tweet update(Tweet tweet) throws SQLException {
        return tweetRepository.update(tweet);
    }

    @Override
    public void deleteById(int id) throws SQLException {
        tweetRepository.deleteById(id);
    }

    @Override
    public Tweet findById(int id) throws SQLException {
        return tweetRepository.findById(id);
    }

    @Override
    public List<Tweet> findByUser(User user) throws SQLException {
        return tweetRepository.findByUser(user);
    }

    @Override
    public List<Tweet> findAll() throws SQLException {
        return tweetRepository.findAll();
    }

    @Override
    public Tweet postTweet(User user, String text, int retweetFromId) throws SQLException {
        Tweet retweetFrom = findById(retweetFromId);
        Tweet tweet = new Tweet(0, text, 0, 0, user, retweetFrom);
        return save(tweet);
    }

    @Override
    public boolean viewMyTweets(User user) throws SQLException {
        List<Tweet> tweets = findByUser(user);
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
        List<Tweet> tweets = findByUser(user);
        Tweet tweet = findById(tweetId);
        if (!tweets.contains(tweet))
            throw new UserDoesNotOwnTweetException();
        deleteById(tweetId);
        return true;
    }

    @Override
    public boolean editTweet(User user, int tweetId, String newText) throws SQLException {
        Tweet tweet = doesUserOwnTweet(user, tweetId);
        if (tweet != null) {
            tweet.setText(newText);
            update(tweet);
            return true;
        }
        return false;
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

    @Override
    public Tweet doesUserOwnTweet(User user, int tweetId) throws SQLException {
        List<Tweet> tweets = findByUser(user);
        Tweet tweet = findById(tweetId);
        if (tweets.contains(tweet))
            return tweet;
        return null;
    }


}
