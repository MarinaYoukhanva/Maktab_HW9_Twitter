package org.example.service.impl;

import org.example.entity.Tag;
import org.example.entity.Tweet;
import org.example.repository.TweetTagRepository;
import org.example.repository.impl.TweetTagRepositoryImpl;
import org.example.service.TweetTagService;

import java.sql.SQLException;
import java.util.List;

public class TweetTagServiceImpl implements TweetTagService {
    TweetTagRepository tweetTagRepository ;

    public TweetTagServiceImpl() throws SQLException {
        tweetTagRepository = new TweetTagRepositoryImpl();
    }

    @Override
    public void save(Tweet tweet, Tag tag) throws SQLException {
        tweetTagRepository.save(tweet, tag);
    }

    @Override
    public void deleteById(int tweetId, int tagId) throws SQLException {
        tweetTagRepository.deleteById(tweetId, tagId);
    }

    @Override
    public List<Tag> findTagsForTweet(int tweetId) throws SQLException {
        return tweetTagRepository.findTagsForTweet(tweetId);
    }
}
