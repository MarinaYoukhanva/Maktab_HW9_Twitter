package org.example.repository.impl;

import org.example.Datasource;
import org.example.entity.Tag;
import org.example.repository.TagRepository;
import org.example.repository.TweetTagRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagRepositoryImpl implements TagRepository {
    TweetTagRepository tweetTagRepository;

    public TagRepositoryImpl() throws SQLException {
        tweetTagRepository = new TweetTagRepositoryImpl();
        initTable();
    }
    private static final String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS tag
            (
                id SERIAL PRIMARY KEY,
                title VARCHAR(50) UNIQUE NOT NULL
            )
            """;

    private static final String INSERT_SQL = """
            INSERT INTO tag (title)
            VALUES(?)
            """;

    private static final String UPDATE_SQL = """
            UPDATE tag
            SET title = ?
            WHERE id = ?
            """;

    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM tag
            WHERE id = ?
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT * FROM tag
            WHERE id = ?
            """;

    private static final String FIND_BY_TITLE_SQL = """
            SELECT * FROM tag
            WHERE title = ?
            """;

    @Override
    public void initTable() throws SQLException {
        var statement = Datasource.getConnection().prepareStatement(CREATE_TABLE);
        statement.execute();
        statement.close();
    }

    @Override
    public Tag save(Tag tag) throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(INSERT_SQL)) {
            statement.setString(1, tag.getTitle());
            statement.execute();
            return tag;
        }
    }

    @Override
    public Tag update(Tag tag) throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(UPDATE_SQL)) {
            statement.setString(1, tag.getTitle());
            statement.setInt(2, tag.getId());
            statement.execute();
            return tag;
        }
    }

    @Override
    public void deleteById(int id) throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(DELETE_BY_ID_SQL)) {
            statement.setInt(1, id);
            var affectedRows = statement.executeUpdate();
            System.out.println("number of tags deleted: " + affectedRows);
        }
    }

    @Override
    public Tag findById(int id) throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Tag tag = null;
            if (resultSet.next()) {
                int tagId = resultSet.getInt(1);
                String tagTitle = resultSet.getString(2);
                tag = new Tag(tagId, tagTitle);
            }
            return tag;
        }
    }


    @Override
    public Tag findByTitle(String title) throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(FIND_BY_TITLE_SQL)) {
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            Tag tag = null;
            if (resultSet.next()) {
                int tagId = resultSet.getInt(1);
                String tagTitle = resultSet.getString(2);
                tag = new Tag(tagId, tagTitle);
            }
            return tag;
        }
    }
}
