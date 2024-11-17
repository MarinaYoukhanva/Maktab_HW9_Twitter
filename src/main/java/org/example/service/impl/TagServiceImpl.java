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
    public Tag save(Tag tag) throws SQLException {
        return tagRepository.save(tag);
    }

    @Override
    public Tag update(Tag tag) throws SQLException {
        return tagRepository.update(tag);
    }

    @Override
    public void deleteById(int id) throws SQLException {
        tagRepository.deleteById(id);
    }

    @Override
    public Tag findById(int id) throws SQLException {
        return tagRepository.findById(id);
    }

    @Override
    public Tag findByTitle(String title) throws SQLException {
        return tagRepository.findByTitle(title);
    }

    @Override
    public void createTag(String title) throws SQLException {
        if (tagRepository.findByTitle(title) != null) {
            Tag tag = new Tag(0, title);
            tagRepository.save(tag);
        }
    }
}
