package org.example.exception;

public class TagDoesNotExistException extends RuntimeException {
    public TagDoesNotExistException() {
        super("Tag does not exist! ");
    }
}
