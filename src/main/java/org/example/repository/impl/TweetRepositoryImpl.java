package org.example.repository.impl;

import org.example.Datasource;
import org.example.entity.Tweet;
import org.example.repository.TweetRepository;

import java.sql.SQLException;

public class TweetRepositoryImpl implements TweetRepository {

    private static final String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS tweet
            (
                id SERIAL PRIMARY KEY,
                text VARCHAR(280) NOT NULL,
                likes INT DEFAULT 0,
                dislikes INT DEFAULT 0,
                user_id INT NOT NULL REFERENCES "user" (id),
                retweet_from_id INT REFERENCES tweet (id)
            )
            """;

    @Override
    public void initTable() throws SQLException {
        var statement = Datasource.getConnection().prepareStatement(CREATE_TABLE);
        statement.execute();
        statement.close();
    }

    @Override
    public Tweet save(Tweet tweet) {
        return null;
    }

    @Override
    public Tweet update(Tweet tweet) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public Tweet findById(int id) {
        return null;
    }
}
