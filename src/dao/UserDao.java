package dao;

import model.User;
import util.DatabaseUtil;
import java.sql.*;

public class UserDao {

    public long nextUserId() {
        String sql = "SELECT COALESCE(MAX(user_id), 1000000000) + 1 FROM users";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(User user) {
        String sql = "INSERT INTO users (user_id, mobile_number, full_name, pin, balance) "
                   + "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, user.getUserId());
            ps.setString(2, user.getMobileNumber());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getPin());
            ps.setDouble(5, user.getBalance());
            ps.executeUpdate();
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
                return null;   // no matching row
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
