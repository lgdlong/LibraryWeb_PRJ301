package db;

import java.sql.*;

public class DbConfig {
    private static final String URL = "jdbc:sqlserver://mssql:1433;databaseName=library_system;encrypt=true;trustServerCertificate=true;";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "12345"; // trùng docker-compose

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ JDBC Driver not found.");
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("❌ Failed to connect to SQL Server. Check credentials or Docker status.");
            throw new RuntimeException(e);
        }
    }
}
