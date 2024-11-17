package org.example.repository.impl;

import org.example.Datasource;
import org.example.entity.Tag;
import org.example.entity.Tweet;
import org.example.repository.TweetTagRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    private static final String INSERT_SQL = """
            INSERT INTO tweet_tag (tweet_id, tag_id)
            VALUES (?, ?)
            """;

    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM tweet_tag
            WHERE tweet_id = ? AND tag_id = ?
            """;

    private static final String FIND_TAGS_FOR_TWEET_SQL = """
            SELECT tag.* FROM tag
                INNER JOIN tweet_tag
                    ON tag.id = tweet_tag.tag_id
            WHERE tweet_tag.tweet_id = ?
            
            """;

    @Override
    public void initTable() throws SQLException {
        var statement = Datasource.getConnection().prepareStatement(CREATE_TABLE);
        statement.execute();
        statement.close();
    }

    @Override
    public void save(Tweet tweet, Tag tag) throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(INSERT_SQL)) {
            statement.setInt(1, tweet.getId());
            statement.setInt(2, tag.getId());
            statement.execute();
        }
    }


    @Override
    public void deleteById(int tweetId, int tagId) throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(DELETE_BY_ID_SQL)) {
            statement.setInt(1, tweetId);
            statement.setInt(2, tagId);
            var affectedRows = statement.executeUpdate();
            System.out.println("number of tags deleted for tweet : " + affectedRows);        }
    }

    @Override
    public List<Tag> findTagsForTweet(int tweetId) throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(FIND_TAGS_FOR_TWEET_SQL)){
            statement.setInt(1, tweetId);
            ResultSet resultSet = statement.executeQuery();
            List<Tag> tagsList = new ArrayList<>();
            while (resultSet.next()) {
                Tag tag = new Tag();
                tag.setId(resultSet.getInt(1));
                tag.setTitle(resultSet.getString(2));
                tagsList.add(tag);
            }
            return tagsList;
        }
    }
}
