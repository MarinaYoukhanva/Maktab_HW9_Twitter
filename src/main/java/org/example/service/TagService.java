package org.example.service;

import java.sql.SQLException;

public interface TagService{
    void createTag(String title) throws SQLException;
}
