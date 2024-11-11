package org.example.repository;

import org.example.entity.Tweet;

import java.sql.SQLException;

public interface TweetRepository {

    void initTable() throws SQLException;
    Tweet save(Tweet tweet) throws SQLException;
    Tweet update(Tweet tweet) throws SQLException;
    void deleteById(int id) throws SQLException;
    Tweet findById(int id) throws SQLException;
}
