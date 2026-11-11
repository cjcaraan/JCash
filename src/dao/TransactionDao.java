package dao;
 
import model.*;
import util.DatabaseUtil;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDao {
    public long insert(String type, double amount, String details) {
        String sql = "INSERT INTO transactions (type, amount, details) "
                    + "VALUES (?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, type);
            ps.setDouble(2, amount);
            ps.setString(3, details);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getLong(1);
                }
                throw new RuntimeException("Transaction logged, but no Transaction ID returned.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Transaction> getTransactionHistory(String mobileNumber) {
        String sql = "SELECT * FROM transactions WHERE details LIKE ?";
        List<Transaction> transactionHistory = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + mobileNumber + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transactionHistory.add(new Transaction(
                                        rs.getLong("transaction_id"),
                                        rs.getString("type"), 
                                        rs.getDouble("amount"), 
                                        rs.getString("details"), 
                                        rs.getString("time_stamp")));
                }
            }
            return transactionHistory;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        
    }
}
