package org.example.service;

import org.example.entity.Tag;
import org.example.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface TagService{

    Tag save(Tag tag);
    Tag update(Tag tag);
    void deleteById(int id);
    Tag findById(int id);
    Tag findByTitle(String title);
    List<Tag> findAll();

    void chooseTag(User user, int tweetId, int TagId);
    void deleteTagForTweet(User user, int tweetId, String title);
    Tag createTag(String title);
}
