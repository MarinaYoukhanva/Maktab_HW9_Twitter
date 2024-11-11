package org.example.repository.impl;

import org.example.Datasource;
import org.example.entity.Tag;
import org.example.repository.TagRepository;

import java.sql.SQLException;

public class TagRepositoryImpl implements TagRepository {

    private static final String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS tag
            (
                id SERIAL PRIMARY KEY,
                title VARCHAR(50) UNIQUE NOT NULL
            )
            """;

    @Override
    public void initTable() throws SQLException {
        var statement = Datasource.getConnection().prepareStatement(CREATE_TABLE);
        statement.execute();
        statement.close();
    }

    @Override
    public Tag save(Tag tag) {
        return null;
    }

    @Override
    public Tag update(Tag tag) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public Tag findById(int id) {
        return null;
    }
}
