package org.example.exception;

public class NoConnectionToDatabase extends RuntimeException{
    public NoConnectionToDatabase() {
        super("No connection to the database! ");
    }
}
