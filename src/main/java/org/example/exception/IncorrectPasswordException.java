package org.example.exception;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException(){
        super("User not found! Try again. ");
    }
}
