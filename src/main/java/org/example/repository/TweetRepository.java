package org.example.repository;

import org.example.entity.Tweet;
import org.example.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface TweetRepository {

    void initTable() throws SQLException;
    Tweet save(Tweet tweet);
    Tweet update(Tweet tweet);
    Tweet removeParent(Tweet tweet);
    void deleteById(int id);
    Tweet findById(int id);
    List<Tweet> findByUser(User user);
    List<Tweet> findRetweets(int parentId);
    List<Tweet> findAll();
}
