package org.example.repository;

import org.example.entity.User;

public interface UserRepository {

    void initTable();
    User save(User user);
    User update(User user);
    void deleteById(int id);
    User findById(int id);
    User findByUsername(String username);


}

