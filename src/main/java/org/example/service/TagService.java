package org.example.service;

import org.example.entity.Tag;

import java.sql.SQLException;

public interface TagService{

    Tag save(Tag tag) throws SQLException;
    Tag update(Tag tag) throws SQLException;
    void deleteById(int id) throws SQLException;
    Tag findById(int id) throws SQLException;
    Tag findByTitle(String title) throws SQLException;

    void createTag(String title) throws SQLException;
}
