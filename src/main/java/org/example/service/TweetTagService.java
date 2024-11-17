package org.example.service;

import org.example.entity.Tag;
import org.example.entity.Tweet;

import java.sql.SQLException;
import java.util.List;

public interface TweetTagService {

    void save(Tweet tweet, Tag tag) throws SQLException;
    void deleteById(int tweetId, int tagId) throws SQLException;
    List<Tag> findTagsForTweet(int tweetId) throws SQLException;
}