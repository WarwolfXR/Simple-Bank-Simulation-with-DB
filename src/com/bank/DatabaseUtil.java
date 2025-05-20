package com.bank;

import java.sql.*;

public class DatabaseUtil {
    private static final String DB_URL = "jdbc:sqlite:db/bank.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC"); // Load SQLite JDBC driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void insertTransaction(String accountNo, String type, double amount) {
    String sql = "INSERT INTO transactions (account_no, type, amount) VALUES (?, ?, ?)";
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, accountNo);
        pstmt.setString(2, type);
        pstmt.setDouble(3, amount);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();  // Print detailed error to console/log
        throw new RuntimeException("Error saving transaction to database.", e);
    }
}


    public static void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS transactions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "account_no TEXT NOT NULL, " +
                "type TEXT NOT NULL, " +
                "amount REAL NOT NULL, " +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
