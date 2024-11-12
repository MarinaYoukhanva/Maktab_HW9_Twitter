package org.example.service.impl;

import org.example.entity.Tag;
import org.example.repository.TagRepository;
import org.example.repository.impl.TagRepositoryImpl;
import org.example.service.TagService;

import java.sql.SQLException;

public class TagServiceImpl implements TagService {
    TagRepository tagRepository;

    public TagServiceImpl () throws SQLException {
        tagRepository = new TagRepositoryImpl();
    }

    @Override
    public void createTag(String title) throws SQLException {
        if (tagRepository.findByTitle(title) != null) {
            Tag tag = new Tag(0, title);
            tagRepository.save(tag);
        }
    }
}
