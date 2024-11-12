package org.example.repository;


import java.sql.SQLException;

public interface TweetTagRepository {

    void initTable() throws SQLException;
    void save() throws SQLException;
    void update() throws SQLException;
    void deleteById() throws SQLException;
}
