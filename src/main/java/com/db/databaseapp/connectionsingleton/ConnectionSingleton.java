package com.db.databaseapp.connectionsingleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum ConnectionSingleton {
    INSTANCE;

    private Connection connection;

    ConnectionSingleton() {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://34.118.45.23:5432/Users";
            String username = "hometask3intensive";
            String password = "rcMMEAsEi991BEaf";
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException exception) {
            System.out.println("Database Connection Creation Failed : " + exception.getMessage());
        }
    }

    public static ConnectionSingleton getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() {
        return connection;
    }
}
