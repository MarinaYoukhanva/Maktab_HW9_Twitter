package org.example.repository.impl;

import org.example.Datasource;
import org.example.repository.TweetTagRepository;

import java.sql.SQLException;

public class TweetTagRepositoryImpl implements TweetTagRepository {

    public TweetTagRepositoryImpl() throws SQLException {
        initTable();
    }

    private static final String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS tweet_tag
            (
                tweet_id INT NOT NULL REFERENCES tweet(id),
                tag_id INT NOT NULL REFERENCES tag(id),
                PRIMARY KEY (tweet_id, tag_id)
            )
            """;

    @Override
    public void initTable() throws SQLException {
        var statement = Datasource.getConnection().prepareStatement(CREATE_TABLE);
        statement.execute();
        statement.close();
    }

    @Override
    public void save() throws SQLException {

    }

    @Override
    public void update() throws SQLException {

    }

    @Override
    public void deleteById() throws SQLException {

    }
}
