package org.example.repository.impl;

import org.example.Datasource;
import org.example.entity.User;
import org.example.repository.UserRepository;

import java.sql.SQLException;

public class UserRepositoryImpl implements UserRepository {

    public UserRepositoryImpl() throws SQLException {
        initTable();
    }

    private static final String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS "user"
            (
                id SERIAL PRIMARY KEY,
                display_name VARCHAR(50) UNIQUE NOT NULL ,
                email VARCHAR(50)  UNIQUE NOT NULL CHECK (email LIKE '%@gmail.com') ,
                username VARCHAR(50) UNIQUE NOT NULL,
                password VARCHAR(50) NOT NULL,
                bio VARCHAR(200),
                account_creation_date DATE NOT NULL DEFAULT current_date
            )
            """;

    @Override
    public void initTable() throws SQLException {
        var statement = Datasource.getConnection().prepareStatement(CREATE_TABLE);
        statement.execute();
        statement.close();
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public User findByUsername(String username) {
        return null;
    }
}
