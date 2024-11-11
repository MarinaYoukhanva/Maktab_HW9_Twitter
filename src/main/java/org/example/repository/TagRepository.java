package org.example.repository;

import org.example.entity.Tag;

import java.sql.SQLException;

public interface TagRepository {

    void initTable() throws SQLException;
    Tag save(Tag tag);
    Tag update(Tag tag);
    void deleteById(int id);
    Tag findById(int id);
}
