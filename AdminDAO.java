package hostel.management.system.dao;

import hostel.management.system.util.DatabaseConnection;
import java.sql.*;

public class AdminDAO {

    public boolean validateLogin(String username, String password) {
        String sql = "SELECT * FROM admin WHERE username=? AND password=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
            return false;
        }
    }

    public String getAdminName(String username) {
        String sql = "SELECT full_name FROM admin WHERE username=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("full_name");
        } catch (SQLException e) { e.printStackTrace(); }
        return "Admin";
    }
}
