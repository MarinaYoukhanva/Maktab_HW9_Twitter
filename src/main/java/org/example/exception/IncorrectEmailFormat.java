package org.example.exception;

public class IncorrectEmailFormat extends RuntimeException{
    public IncorrectEmailFormat() {
        super("Incorrect email format! ");
    }
}
