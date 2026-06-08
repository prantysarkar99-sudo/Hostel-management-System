package hostel.management.system.dao;

import hostel.management.system.model.Complaint;
import hostel.management.system.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDAO {

    
    public boolean addComplaint(Complaint complaint) {
        String sql = "INSERT INTO complaints (complaint_id, student_id, subject, description, category, status) " +
                     "VALUES (?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, complaint.getComplaintId());
            ps.setString(2, complaint.getStudentId());
            ps.setString(3, complaint.getSubject());
            ps.setString(4, complaint.getDescription());
            ps.setString(5, complaint.getCategory());
            ps.setString(6, complaint.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding complaint: " + e.getMessage());
            return false;
        }
    }

   
    public boolean updateComplaintStatus(String complaintId, String status) {
        String sql = "UPDATE complaints SET status=?, resolved_date=? WHERE complaint_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, status.equals("Resolved") ? new java.util.Date().toString() : null);
            ps.setString(3, complaintId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating complaint: " + e.getMessage());
            return false;
        }
    }

    
    public List<Complaint> getAllComplaints() {
        List<Complaint> list = new ArrayList<>();
        String sql = "SELECT c.*, s.full_name FROM complaints c " +
                     "LEFT JOIN students s ON c.student_id = s.student_id ORDER BY c.submitted_date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapComplaint(rs));
        } catch (SQLException e) {
            System.err.println("Error fetching complaints: " + e.getMessage());
        }
        return list;
    }

    public int getPendingComplaints() {
        String sql = "SELECT COUNT(*) FROM complaints WHERE status='Pending'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public String generateComplaintId() {
        String sql = "SELECT COUNT(*) FROM complaints";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                int count = rs.getInt(1) + 1;
                return String.format("CMP%04d", count);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return "CMP0001";
    }

    private Complaint mapComplaint(ResultSet rs) throws SQLException {
        Complaint c = new Complaint();
        c.setId(rs.getInt("id"));
        c.setComplaintId(rs.getString("complaint_id"));
        c.setStudentId(rs.getString("student_id"));
        c.setSubject(rs.getString("subject"));
        c.setDescription(rs.getString("description"));
        c.setCategory(rs.getString("category"));
        c.setStatus(rs.getString("status"));
        c.setSubmittedDate(rs.getString("submitted_date"));
        return c;
    }
}
