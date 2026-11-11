
package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String URL = "jdbc:sqlite:data/database.db";

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
        }
        return conn;
    }

    public static void initialize() {
        try {
            Files.createDirectories(Path.of("data"));   // SQLite won't create the folder itself
            try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS users ("
                        + "user_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "mobile_number TEXT NOT NULL UNIQUE, "
                        + "full_name TEXT NOT NULL, "
                        + "pin TEXT NOT NULL, "
                        + "balance REAL NOT NULL DEFAULT 0)");
                stmt.execute("CREATE TABLE IF NOT EXISTS transactions ("
                        + "transaction_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "type TEXT NOT NULL CHECK (type IN ('CASH_IN', 'WITHDRAW', 'TRANSFER')), "
                        + "amount REAL NOT NULL CHECK (amount > 0), "
                        + "details TEXT NOT NULL, "
                        + "time_stamp TEXT NOT NULL DEFAULT (datetime('now', '+8 hours')))");
                stmt.execute("INSERT INTO sqlite_sequence (name, seq) "
                        + "SELECT 'users', 1000000000 "
                        + "WHERE NOT EXISTS (SELECT 1 FROM sqlite_sequence WHERE name = 'users')");
                stmt.execute("INSERT INTO sqlite_sequence (name, seq) "
                        + "SELECT 'transactions', 2000000000 "
                        + "WHERE NOT EXISTS (SELECT 1 FROM sqlite_sequence WHERE name = 'transactions')");
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException("Could not set up the database", e);
        }
    }
}