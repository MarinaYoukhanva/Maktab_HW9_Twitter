package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Datasource {

    private static final Connection connection;

    private Datasource() {
    }

    static {
        var jdbc = "jdbc:postgresql://localhost:5432/twitter";
        try {
            connection = DriverManager.getConnection(jdbc, "postgres", "postgres");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
