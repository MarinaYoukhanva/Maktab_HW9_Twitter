package org.example.exception;

public class TweetDoesNotHaveThisTagException extends RuntimeException{
    public TweetDoesNotHaveThisTagException(){
        super("Tweet does not have this tag! ");
    }
}
