package at.technikum.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String CONNECTION_STRING = "jdbc:postgresql://localhost:5432/postgres";
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(CONNECTION_STRING, "postgres","postgres");
    }
}
