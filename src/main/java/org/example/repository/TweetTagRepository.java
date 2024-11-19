package org.example.repository;


import org.example.entity.Tag;
import org.example.entity.Tweet;

import java.sql.SQLException;
import java.util.List;

public interface TweetTagRepository {

    void initTable() throws SQLException;
    void save(Tweet tweet, Tag tag);
    void deleteById(int tweetId, int tagId);
    List<Tag> findTagsForTweet(int tweetId);
}
