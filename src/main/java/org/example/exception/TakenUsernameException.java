package org.example.exception;

public class TakenUsernameException extends RuntimeException{
    public TakenUsernameException(){
        super("This Username is already taken! Chose another one.");
    }
}
