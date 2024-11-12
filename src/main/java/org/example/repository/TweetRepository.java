package org.example.repository;

import org.example.entity.Tweet;
import org.example.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface TweetRepository {

    void initTable() throws SQLException;
    Tweet save(Tweet tweet) throws SQLException;
    Tweet update(Tweet tweet) throws SQLException;
    void deleteById(int id) throws SQLException;
    Tweet findById(int id) throws SQLException;
    List<Tweet> findByUser(User user) throws SQLException;
    List<Tweet> findAll() throws SQLException;
}
