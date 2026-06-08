package hostel.management.system.dao;

import hostel.management.system.model.Payment;
import hostel.management.system.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    
    public boolean addPayment(Payment payment) {
        String sql = "INSERT INTO payments (payment_id, student_id, amount, payment_type, " +
                     "payment_method, payment_date, month_year, description) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, payment.getPaymentId());
            ps.setString(2, payment.getStudentId());
            ps.setDouble(3, payment.getAmount());
            ps.setString(4, payment.getPaymentType());
            ps.setString(5, payment.getPaymentMethod());
            ps.setString(6, payment.getPaymentDate());
            ps.setString(7, payment.getMonthYear());
            ps.setString(8, payment.getDescription());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding payment: " + e.getMessage());
            return false;
        }
    }

  
    public List<Payment> getAllPayments() {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT p.*, s.full_name FROM payments p " +
                     "LEFT JOIN students s ON p.student_id = s.student_id ORDER BY p.created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapPayment(rs));
        } catch (SQLException e) {
            System.err.println("Error fetching payments: " + e.getMessage());
        }
        return list;
    }

    
    public List<Payment> getPaymentsByStudent(String studentId) {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM payments WHERE student_id=? ORDER BY payment_date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapPayment(rs));
        } catch (SQLException e) {
            System.err.println("Error fetching payments: " + e.getMessage());
        }
        return list;
    }

    public double getTotalRevenue() {
        String sql = "SELECT SUM(amount) FROM payments";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public String generatePaymentId() {
        String sql = "SELECT COUNT(*) FROM payments";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                int count = rs.getInt(1) + 1;
                return String.format("PAY%04d", count);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return "PAY0001";
    }

    private Payment mapPayment(ResultSet rs) throws SQLException {
        Payment p = new Payment();
        p.setId(rs.getInt("id"));
        p.setPaymentId(rs.getString("payment_id"));
        p.setStudentId(rs.getString("student_id"));
        p.setAmount(rs.getDouble("amount"));
        p.setPaymentType(rs.getString("payment_type"));
        p.setPaymentMethod(rs.getString("payment_method"));
        p.setPaymentDate(rs.getString("payment_date"));
        p.setMonthYear(rs.getString("month_year"));
        p.setDescription(rs.getString("description"));
        return p;
    }
}
