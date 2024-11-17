package org.example.repository.impl;

import org.example.Datasource;
import org.example.entity.Tweet;
import org.example.entity.User;
import org.example.repository.TweetRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TweetRepositoryImpl implements TweetRepository {

    UserRepositoryImpl userRepository = new UserRepositoryImpl();

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

    private static final String INSERT_SQL = """
            INSERT INTO tweet (text, user_id, retweet_from_id)
            VALUES(?, ?, ?)
            """;

    private static final String UPDATE_SQL = """
            UPDATE tweet
            SET text = ?, likes = ?, dislikes = ?
            WHERE id = ?
            """;

    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM tweet
            WHERE id = ?
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT * FROM tweet
            WHERE id = ?
            """;

    private static final String FIND_BY_USER_SQL = """
            SELECT * FROM tweet
            WHERE user_id = ?
            """;
    private static final String SHOW_TWEETS_SQL = """
            SELECT * FROM tweet
            ORDER BY id DESC
            """;


    public TweetRepositoryImpl() throws SQLException {
        initTable();
    }

    @Override
    public void initTable() throws SQLException {
        var statement = Datasource.getConnection().prepareStatement(CREATE_TABLE);
        statement.execute();
        statement.close();
    }

    @Override
    public Tweet save(Tweet tweet) throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(INSERT_SQL,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, tweet.getText());
            statement.setInt(2, tweet.getUser().getId());
            if (tweet.getRetweetFrom() != null)
                statement.setInt(3, tweet.getRetweetFrom().getId());
            else statement.setNull(3, java.sql.Types.INTEGER);
            statement.execute();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next())
                    tweet.setId(generatedKeys.getInt(1));
                return tweet;
            }
        }
    }

    @Override
    public Tweet update(Tweet tweet) throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(UPDATE_SQL)) {
            statement.setString(1, tweet.getText());
            statement.setInt(2, tweet.getLikes());
            statement.setInt(3, tweet.getDislikes());
            statement.setInt(4, tweet.getId());
            statement.execute();
            return tweet;
        }
    }

    @Override
    public void deleteById(int id) throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(DELETE_BY_ID_SQL)) {
            statement.setInt(1, id);
            var affectedRows = statement.executeUpdate();
            System.out.println("number of tweets deleted: " + affectedRows);
        }
    }

    @Override
    public Tweet findById(int id) throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Tweet tweet = null;
            if (resultSet.next()) {
                tweet = getTweetInfo (resultSet);
            }
            return tweet;
        }
    }

    @Override
    public List<Tweet> findByUser(User user) throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(FIND_BY_USER_SQL)) {
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            List<Tweet> tweets = new ArrayList<>();
            while (resultSet.next()) {
                int tweetId = resultSet.getInt(1);
                String text = resultSet.getString(2);
                int likes = resultSet.getInt(3);
                int dislikes = resultSet.getInt(4);
                int retweetFromId = resultSet.getInt(6);
                Tweet retweetFrom = null;
                if (retweetFromId != 0) {
                    retweetFrom = findById(retweetFromId);
                }
                Tweet tweet = new Tweet(tweetId, text, likes, dislikes, user, retweetFrom);
                tweets.add(tweet);
            }
            return tweets;
        }
    }
    @Override
    public List<Tweet> findAll() throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(SHOW_TWEETS_SQL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Tweet> tweets = new ArrayList<>();
            while (resultSet.next()) {
                Tweet tweet = getTweetInfo(resultSet);
                tweets.add(tweet);
            }
            return tweets;
        }
    }
    private Tweet getTweetInfo (ResultSet resultSet) throws SQLException {
        int tweetId = resultSet.getInt(1);
        String text = resultSet.getString(2);
        int likes = resultSet.getInt(3);
        int dislikes = resultSet.getInt(4);
        int userId = resultSet.getInt(5);
        int retweetFromId = resultSet.getInt(6);
        User user = userRepository.findById(userId);
        Tweet retweetFrom = null;
        if (retweetFromId != 0) {
            retweetFrom = findById(retweetFromId);
        }
        return new Tweet(tweetId, text, likes, dislikes, user, retweetFrom);
    }
}
