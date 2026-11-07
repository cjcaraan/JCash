
package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String URL = "jdbc:sqlite:data/bank.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initialize() {
        try {
            Files.createDirectories(Path.of("data"));   // SQLite won't create the folder itself
            try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS users ("
                        + "user_id INTEGER PRIMARY KEY, "
                        + "mobile_number TEXT NOT NULL UNIQUE, "
                        + "full_name TEXT NOT NULL, "
                        + "pin TEXT NOT NULL, "
                        + "balance REAL NOT NULL DEFAULT 0)");
                stmt.execute("CREATE TABLE IF NOT EXISTS transactions ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "user_id INTEGER NOT NULL, "
                        + "description TEXT NOT NULL, "
                        + "FOREIGN KEY (user_id) REFERENCES users(user_id))");
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException("Could not set up the database", e);
        }
    }
}