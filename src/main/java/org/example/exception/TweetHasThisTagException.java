package org.example.exception;

public class TweetHasThisTagException extends RuntimeException {
    public TweetHasThisTagException() {
        super("Tweet already has this tag! ");
    }
}
