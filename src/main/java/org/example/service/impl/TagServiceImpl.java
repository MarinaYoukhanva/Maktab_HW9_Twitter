package org.example.service.impl;

import org.example.entity.Tag;
import org.example.entity.Tweet;
import org.example.entity.User;
import org.example.repository.TagRepository;
import org.example.repository.impl.TagRepositoryImpl;
import org.example.service.TagService;
import org.example.service.TweetService;
import org.example.service.TweetTagService;

import java.sql.SQLException;
import java.util.List;

public class TagServiceImpl implements TagService {
    TagRepository tagRepository;
    TweetService tweetService;
    TweetTagService tweetTagService;

    public TagServiceImpl () throws SQLException {
        tagRepository = new TagRepositoryImpl();
        tweetService = new TweetServiceImpl();
        tweetTagService = new TweetTagServiceImpl();
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
    public List<Tag> findAll() throws SQLException {
        return tagRepository.findAll();
    }

    @Override
    public void chooseTag(User user, int tweetId, int tagId) throws SQLException {
        Tweet tweet = tweetService.doesUserOwnTweet(user, tweetId);
        if (tweet != null) {
            Tag tag = hasTweetTheTag(tweetId, tagId);
            if (tag != null) {
                tweetTagService.save(tweet, tag);
                tweet.getTags().add(tag);
            }
        }
    }

    @Override
    public void deleteTagForTweet(User user, int tweetId, int tagId) throws SQLException {
        Tweet tweet = tweetService.doesUserOwnTweet(user, tweetId);
        if (tweet != null) {
            Tag tag = hasTweetTheTag(tweetId, tagId);
            if (tag != null) {
                tweetTagService.deleteById(tweetId, tagId);
                tweet.getTags().remove(tag);
            }
        }
    }

    @Override
    public Tag createTag(String title) throws SQLException {
        if (tagRepository.findByTitle(title) == null) {
            Tag tag = new Tag(0, title);
            return tagRepository.save(tag);
        }
        return null;
    }

    private Tag hasTweetTheTag(int tweetId, int tagId) throws SQLException {
        List<Tag> tags = tweetTagService.findTagsForTweet(tweetId);
        Tag tag = findById(tagId);
        if (!tags.contains(tag))
            return tag;
        return null;
    }
}
