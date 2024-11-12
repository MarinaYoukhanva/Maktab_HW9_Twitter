package org.example;


import org.example.view.View;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;


public class Main {

    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException {

        View view = new View();
        while (true) {
            view.menu();
        }
    }
}






