package org.example.repository;

import org.example.entity.Tag;

public interface TagRepository {

    void initTable();
    Tag save(Tag tag);
    Tag update(Tag tag);
    void deleteById(int id);
    Tag findById(int id);
}
