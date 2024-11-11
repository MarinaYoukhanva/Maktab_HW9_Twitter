package org.example.repository;

import org.example.entity.User;

import java.sql.SQLException;

public interface UserRepository {

    void initTable() throws SQLException;
    User save(User user);
    User update(User user);
    void deleteById(int id);
    User findById(int id);
    User findByUsername(String username);


}

