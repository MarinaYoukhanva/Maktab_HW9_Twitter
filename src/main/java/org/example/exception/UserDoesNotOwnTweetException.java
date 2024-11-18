package org.example.exception;

public class UserDoesNotOwnTweetException extends RuntimeException{
    public UserDoesNotOwnTweetException() {
        super("User does not own tweet with this id! ");
    }
}
