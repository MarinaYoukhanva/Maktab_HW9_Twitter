package org.example.repository.impl;

import org.example.Datasource;
import org.example.entity.Tag;
import org.example.exception.TagDoesNotExistException;
import org.example.repository.TagRepository;
import org.example.repository.TweetTagRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    private static final String SHOW_ALL_TAGS_SQL = """
            SELECT * FROM tag
            """;

    @Override
    public void initTable() throws SQLException {
        var statement = Datasource.getConnection().prepareStatement(CREATE_TABLE);
        statement.execute();
        statement.close();
    }

    @Override
    public Tag save(Tag tag) throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(INSERT_SQL,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, tag.getTitle());
            statement.execute();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next())
                    tag.setId(generatedKeys.getInt(1));
                return tag;
            }
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
            return getTagInfo(statement);
        }
    }

    @Override
    public Tag findByTitle(String title) throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(FIND_BY_TITLE_SQL)) {
            statement.setString(1, title);
            return getTagInfo(statement);
        }
    }


    @Override
    public List<Tag> findAll() throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(SHOW_ALL_TAGS_SQL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Tag> tags = new ArrayList<>();
            while (resultSet.next()) {
                int tagId = resultSet.getInt(1);
                String title = resultSet.getString(2);
                tags.add(new Tag(tagId, title));
            }
            return tags;
        }
    }

    private Tag getTagInfo(PreparedStatement statement) throws SQLException {
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
