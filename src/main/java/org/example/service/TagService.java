package org.example.service;

import org.example.entity.Tag;
import org.example.entity.User;

import java.sql.SQLException;

public interface TagService{

    Tag save(Tag tag) throws SQLException;
    Tag update(Tag tag) throws SQLException;
    void deleteById(int id) throws SQLException;
    Tag findById(int id) throws SQLException;
    Tag findByTitle(String title) throws SQLException;

    void chooseTag(User user, int tweetId, int TagId) throws SQLException;
    void deleteTagForTweet(User user, int tweetId, int tagId) throws SQLException;
    void createTag(String title) throws SQLException;
}
