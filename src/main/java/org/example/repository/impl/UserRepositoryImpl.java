package org.example.repository.impl;

import org.example.Datasource;
import org.example.entity.User;
import org.example.exception.IncorrectEmailFormat;
import org.example.exception.UserNotFoundException;
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
                display_name VARCHAR(50) NOT NULL ,
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

    private static final String FIND_BY_EMAIL_SQL = """
            SELECT * FROM "user"
            WHERE email = ?
            """;

    @Override
    public void initTable() throws SQLException {
        var statement = Datasource.getConnection().prepareStatement(CREATE_TABLE);
        statement.execute();
        statement.close();
    }

    @Override
    public User save(User user) throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(INSERT_SQL,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            setUserInfo(user, statement);
            statement.execute();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next())
                    user.setId(generatedKeys.getInt(1));
                return user;
            }
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
                user = getUserInfo(resultSet);
            }
            return user;
        }
    }

    @Override
    public User findByUsername(String username) {
        return findUser(username, FIND_BY_USERNAME_SQL);
    }

    @Override
    public User findByEmail(String email) {
        if (!email.endsWith("@gmail.com"))
            throw new IncorrectEmailFormat();
        return findUser(email, FIND_BY_EMAIL_SQL);
    }

    private User findUser(String columnName, String findSQL) {
        User user;
        try (var statement = Datasource.getConnection().prepareStatement(findSQL)) {
            statement.setString(1, columnName);
            ResultSet resultSet = statement.executeQuery();
            try {
                //resultSet.next();
                user = getUserInfo(resultSet);
            } catch (RuntimeException e) {
                throw new UserNotFoundException();
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return user;
    }

    private void setUserInfo(User user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getDisplayName());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getUsername());
        statement.setString(4, user.getPassword());
        statement.setString(5, user.getBio());
    }

    private User getUserInfo(ResultSet resultSet) {
        try {
            int userId = resultSet.getInt(1);
            String displayName = resultSet.getString(2);
            String email = resultSet.getString(3);
            String username = resultSet.getString(4);
            String password = resultSet.getString(5);
            String bio = resultSet.getString(6);
            LocalDate creationDate = resultSet.getDate(7).toLocalDate();
            return new User(userId, displayName, email, username, password, bio, creationDate);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
