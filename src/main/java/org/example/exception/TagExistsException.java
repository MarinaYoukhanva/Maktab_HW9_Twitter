package org.example.exception;

public class TagExistsException extends RuntimeException{
    public TagExistsException(){
        super("Tag already exists! ");
    }
}
