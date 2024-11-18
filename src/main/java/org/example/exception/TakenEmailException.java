package org.example.exception;

public class TakenEmailException extends RuntimeException{
    public TakenEmailException(){
        super("This Email is already signed up! ");
    }
}
