package org.example.repository;

import org.example.entity.User;

import java.sql.SQLException;

public interface UserRepository {

    void initTable() throws SQLException;
    User save(User user) throws SQLException;
    User update(User user) throws SQLException;
    void deleteById(int id) throws SQLException;
    User findById(int id) throws SQLException;
    User findByUsername(String username) throws SQLException;
    User findByEmail(String email) throws SQLException;


}

