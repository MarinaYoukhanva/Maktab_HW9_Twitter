package org.example.service.impl;

import org.example.entity.Tag;
import org.example.entity.Tweet;
import org.example.entity.User;
import org.example.exception.TagDoesNotExistException;
import org.example.exception.TagExistsException;
import org.example.exception.TweetDoesNotHaveThisTagException;
import org.example.exception.TweetHasThisTagException;
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

    public TagServiceImpl() throws SQLException {
        tagRepository = new TagRepositoryImpl();
        tweetService = new TweetServiceImpl();
        tweetTagService = new TweetTagServiceImpl();
    }

    @Override
    public Tag save(Tag tag){
        return tagRepository.save(tag);
    }

    @Override
    public Tag update(Tag tag){
        return tagRepository.update(tag);
    }

    @Override
    public void deleteById(int id){
        tagRepository.deleteById(id);
    }

    @Override
    public Tag findById(int id){
        return tagRepository.findById(id);
    }

    @Override
    public Tag findByTitle(String title){
        return tagRepository.findByTitle(title);
    }


    @Override
    public List<Tag> findAll(){
        return tagRepository.findAll();
    }

    @Override
    public void chooseTag(User user, int tweetId, int tagId){
        Tweet tweet = tweetService.doesUserOwnTweet(user, tweetId);
        Tag tag = findById(tagId);
        if (tag == null)
            throw new TagDoesNotExistException();
        if (hasTweetTheTag(tweetId, tagId))
            throw new TweetHasThisTagException();
        tweetTagService.save(tweet, tag);
        tweet.getTags().add(tag);

    }

    @Override
    public void deleteTagForTweet(User user, int tweetId, String title){
        Tweet tweet = tweetService.doesUserOwnTweet(user, tweetId);
        Tag tag = findByTitle(title);
        if (tag == null)
            throw new TagDoesNotExistException();
        if (!hasTweetTheTag(tweetId, tag.getId()))
            throw new TweetDoesNotHaveThisTagException();
        tweetTagService.deleteById(tweetId, tag.getId());
        tweet.getTags().remove(tag);

    }

    @Override
    public Tag createTag(String title){
        if (findByTitle(title) != null)
            throw new TagExistsException();
        Tag tag = new Tag(0, title);
        return save(tag);
    }

    private boolean hasTweetTheTag(int tweetId, int tagId) {
        List<Tag> tags = tweetTagService.findTagsForTweet(tweetId);
        Tag tag = findById(tagId);
        return tags.contains(tag);
    }
}
