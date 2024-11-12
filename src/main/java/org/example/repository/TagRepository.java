package org.example.repository;

import org.example.entity.Tag;

import java.sql.SQLException;

public interface TagRepository {

    void initTable() throws SQLException;
    Tag save(Tag tag) throws SQLException;
    Tag update(Tag tag) throws SQLException;
    void deleteById(int id) throws SQLException;
    Tag findById(int id) throws SQLException;

    Tag findByTitle(String title) throws SQLException;
}
