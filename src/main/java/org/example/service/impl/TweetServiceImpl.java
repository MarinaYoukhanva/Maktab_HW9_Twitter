package org.example.service.impl;

import org.example.entity.Tag;
import org.example.entity.Tweet;
import org.example.entity.User;
import org.example.exception.TweetListIsEmptyException;
import org.example.exception.UserDoesNotOwnTweetException;
import org.example.repository.TweetRepository;
import org.example.repository.impl.TweetRepositoryImpl;
import org.example.service.TweetService;
import org.example.service.TweetTagService;

import java.sql.SQLException;
import java.util.List;

public class TweetServiceImpl implements TweetService {

    TweetRepository tweetRepository;
    TweetTagService tweetTagService;

    public TweetServiceImpl() throws SQLException {
        tweetRepository = new TweetRepositoryImpl();
        tweetTagService = new TweetTagServiceImpl();
    }

    @Override
    public Tweet save(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    @Override
    public Tweet update(Tweet tweet) {
        return tweetRepository.update(tweet);
    }
    @Override
    public Tweet removeParent(Tweet tweet){
        return tweetRepository.removeParent(tweet);
    }

    @Override
    public void deleteById(int id)  {
        tweetRepository.deleteById(id);
    }

    @Override
    public Tweet findById(int id)  {
        return tweetRepository.findById(id);
    }

    @Override
    public List<Tweet> findByUser(User user)  {
        return tweetRepository.findByUser(user);
    }
    @Override
    public List<Tweet> findRetweets(int parentId) {
        return tweetRepository.findRetweets(parentId);
    }

    @Override
    public List<Tweet> findAll() {
        return tweetRepository.findAll();
    }

    @Override
    public Tweet postTweet(User user, String text, int retweetFromId) {
        Tweet retweetFrom = findById(retweetFromId);
        Tweet tweet = new Tweet(0, text, 0, 0, user, retweetFrom);
        return save(tweet);
    }

    @Override
    public void viewMyTweets(User user) {
        List<Tweet> tweets = findByUser(user);
        if (tweets.isEmpty())
            throw new TweetListIsEmptyException();
        showTweetList(tweets);
    }

    @Override
    public void deleteTweet(User user, int tweetId) {
        doesUserOwnTweet(user, tweetId);
        List<Tag> tags = tweetTagService.findTagsForTweet(tweetId);
        for (Tag tag : tags)
            tweetTagService.deleteById(tweetId, tag.getId());
        List<Tweet> retweets = findRetweets(tweetId);
        for (Tweet retweet : retweets){
            removeParent(retweet);
            retweet.setRetweetFrom(null);
        }
        deleteById(tweetId);
    }

    @Override
    public void editTweet(User user, int tweetId, String newText) {
        Tweet tweet;
        try {
            tweet = doesUserOwnTweet(user, tweetId);
            tweet.setText(newText);
            update(tweet);
        } catch (NullPointerException e) {
            throw new UserDoesNotOwnTweetException();
        }
    }

    @Override
    public void viewAllTweets() {
        List<Tweet> tweets = findAll();
        showTweetList(tweets);
    }

    @Override
    public void likeTweet(int tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId);
        tweet.setLikes(tweet.getLikes() + 1);
        tweetRepository.update(tweet);
    }

    @Override
    public void dislikeTweet(int tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId);
        tweet.setDislikes(tweet.getDislikes() + 1);
        tweetRepository.update(tweet);
    }

    @Override
    public Tweet doesUserOwnTweet(User user, int tweetId) {
        List<Tweet> tweets = findByUser(user);
        Tweet tweet = findById(tweetId);
        if (!tweets.contains(tweet))
            throw new UserDoesNotOwnTweetException();
        return tweet;
    }

    private void showTweetList(List<Tweet> tweets) {
        for (Tweet tweet : tweets) {
            try {
                tweet.setText(tweet.getRetweetFrom().getText() + "'  '" + tweet.getText());
            } catch (NullPointerException _) {
            } finally {
                System.out.println(tweet);
            }
        }
    }


}
