package org.example.repository.impl;

import org.example.entity.User;
import org.example.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {

    private static final String CREATE_TABLE = """
            
            """;

    @Override
    public void initTable() {

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
