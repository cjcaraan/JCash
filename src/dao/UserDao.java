package dao;

import model.User;
import util.DatabaseUtil;
import java.sql.*;

public class UserDao {

    // public long nextUserId() {
    //     String sql = "SELECT COALESCE(MAX(user_id), 1000000000) + 1 FROM users";
    //     try (Connection conn = DatabaseUtil.getConnection();
    //             Statement stmt = conn.createStatement();
    //             ResultSet rs = stmt.executeQuery(sql)) {
    //         rs.next();
    //         return rs.getLong(1);
    //     } catch (SQLException e) {
    //         throw new RuntimeException(e);
    //     }
    // }

    public long insert(String mobileNumber, String fullName, String pin, double balance) {
        String sql = "INSERT INTO users (mobile_number, full_name, pin, balance) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mobileNumber);
            ps.setString(2, fullName);
            ps.setString(3, pin);
            ps.setDouble(4, balance);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getLong(1);
                }
                throw new RuntimeException("Insert to Database worked, but no ID was returned");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findByMobile(String mobileNumber) {
        String sql = "SELECT user_id, mobile_number, full_name, pin, balance "
                + "FROM users WHERE mobile_number = ?";
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mobileNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getLong("user_id"), rs.getString("mobile_number"),
                            rs.getString("full_name"), rs.getString("pin"),
                            rs.getDouble("balance"));
                }
                return null; // no matching row
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isValidMobileNumber(String mobileNumber) {
        String sql = "SELECT 1 FROM users WHERE mobile_number = ? LIMIT 1";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mobileNumber);

            try (ResultSet rs = ps.executeQuery()) {
                // will return true if atleast one row exist (meaning the mobile number itself
                // exist), false if not.
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCorrectPin(String mobileNumber) {
        String sql = "SELECT pin "
                + "FROM users WHERE mobile_number = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, mobileNumber);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) { // if a row came up:
                    return rs.getString("pin");
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public double getBalance(String mobileNumber) {
        String sql = "SELECT balance FROM users WHERE mobile_number = ?";

        try (Connection conn = DatabaseUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, mobileNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                }
                else { throw new IllegalArgumentException("Couldn't find account. [ERROR from getbalance(String mobileNumber)]"); }
            } 
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void adjustBalance(String mobileNumber, double amount) {
        String sql = "UPDATE users SET balance = balance + ? WHERE mobile_number = ?";

        try (Connection conn = DatabaseUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setDouble(1, amount);
                ps.setString(2, mobileNumber);

                if (ps.executeUpdate() == 0) {      // theoretically this should not be true but redundancy :)
                    throw new IllegalArgumentException("No account found for that mobile number.");
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
