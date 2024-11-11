package org.example.repository;

import org.example.entity.Tweet;

public interface TweetRepository {

    void initTable();
    Tweet save(Tweet tweet);
    Tweet update(Tweet tweet);
    void deleteById(int id);
    Tweet findById(int id);
}
