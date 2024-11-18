package org.example.exception;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException() {
        super("Invalid input! ");
    }
}
