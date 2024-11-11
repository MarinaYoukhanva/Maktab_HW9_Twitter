package org.example.repository;

import org.example.entity.Tweet;

import java.sql.SQLException;

public interface TweetRepository {

    void initTable() throws SQLException;
    Tweet save(Tweet tweet);
    Tweet update(Tweet tweet);
    void deleteById(int id);
    Tweet findById(int id);
}
