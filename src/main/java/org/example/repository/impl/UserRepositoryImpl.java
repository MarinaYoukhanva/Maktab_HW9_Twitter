package org.example.repository.impl;

import org.example.Datasource;
import org.example.entity.User;
import org.example.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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
                account_creation_date DATE NOT NULL DEFAULT (current_date)
            )
            """;

    private static final String INSERT_SQL = """
            INSERT INTO "user" (display_name, email, username, password, bio)
            VALUES(?, ?, ?, ?, ?)
            """;

    private static final String UPDATE_SQL = """
            UPDATE "user"
            SET display_name = ?, email = ?, username = ?, password = ?, bio = ?
            WHERE id = ?
            """;

    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM "user"
            WHERE id = ?
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT * FROM "user"
            WHERE id = ?
            """;

    private static final String FIND_BY_USERNAME_SQL = """
            SELECT * FROM "user"
            WHERE username = ?
            """;

    @Override
    public void initTable() throws SQLException {
        var statement = Datasource.getConnection().prepareStatement(CREATE_TABLE);
        statement.execute();
        statement.close();
    }

    @Override
    public User save(User user) throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(INSERT_SQL)) {
            setUserInfo(user, statement);
            statement.execute();
            return user;
        }
    }

    @Override
    public User update(User user) throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(UPDATE_SQL)) {
            setUserInfo(user, statement);
            statement.setInt(6, user.getId());
            statement.execute();
            return user;
        }
    }

    @Override
    public void deleteById(int id) throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(DELETE_BY_ID_SQL)) {
            statement.setInt(1, id);
            var affectedRows = statement.executeUpdate();
            System.out.println("number of users deleted: " + affectedRows);
        }
    }

    @Override
    public User findById(int id) throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = getUserInfo (resultSet);
            }
            return user;
        }
    }

    @Override
    public User findByUsername(String username) throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(FIND_BY_USERNAME_SQL)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = getUserInfo (resultSet);
            }
            return user;
        }
    }

    private void setUserInfo(User user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getDisplayName());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getUsername());
        statement.setString(4, user.getPassword());
        statement.setString(5, user.getBio());
    }

    private User getUserInfo (ResultSet resultSet) throws SQLException {
        int userId = resultSet.getInt(1);
        String displayName = resultSet.getString(2);
        String email = resultSet.getString(3);
        String username = resultSet.getString(4);
        String password = resultSet.getString(5);
        String bio = resultSet.getString(6);
        LocalDate creationDate = resultSet.getDate(7).toLocalDate();
        return new User(userId, displayName, email, username, password, bio, creationDate);
    }
}
