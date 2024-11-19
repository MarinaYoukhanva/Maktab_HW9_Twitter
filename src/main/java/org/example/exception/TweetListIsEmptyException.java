package org.example.exception;

public class TweetListIsEmptyException extends RuntimeException{
    public TweetListIsEmptyException(){
        super("Tweet List is empty! ");
    }
}
