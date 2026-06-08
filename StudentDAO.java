package hostel.management.system.dao;

import hostel.management.system.model.Student;
import hostel.management.system.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students (student_id, full_name, email, phone, address, " +
                     "guardian_name, guardian_phone, course, admission_date, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getStudentId());
            ps.setString(2, student.getFullName());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getPhone());
            ps.setString(5, student.getAddress());
            ps.setString(6, student.getGuardianName());
            ps.setString(7, student.getGuardianPhone());
            ps.setString(8, student.getCourse());
            ps.setString(9, student.getAdmissionDate());
            ps.setString(10, student.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
            return false;
        }
    }

    
    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET full_name=?, email=?, phone=?, address=?, " +
                     "guardian_name=?, guardian_phone=?, course=?, admission_date=?, status=? " +
                     "WHERE student_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getFullName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getPhone());
            ps.setString(4, student.getAddress());
            ps.setString(5, student.getGuardianName());
            ps.setString(6, student.getGuardianPhone());
            ps.setString(7, student.getCourse());
            ps.setString(8, student.getAdmissionDate());
            ps.setString(9, student.getStatus());
            ps.setString(10, student.getStudentId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
            return false;
        }
    }

   
    public boolean deleteStudent(String studentId) {
        String sql = "DELETE FROM students WHERE student_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, studentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
            return false;
        }
    }

    
    public Student getStudentById(String studentId) {
        String sql = "SELECT * FROM students WHERE student_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, studentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapStudent(rs);
        } catch (SQLException e) {
            System.err.println("Error fetching student: " + e.getMessage());
        }
        return null;
    }

   
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY student_id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapStudent(rs));
        } catch (SQLException e) {
            System.err.println("Error fetching students: " + e.getMessage());
        }
        return list;
    }

    
    public List<Student> searchStudents(String keyword) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE student_id LIKE ? OR full_name LIKE ? OR phone LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setString(3, kw);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapStudent(rs));
        } catch (SQLException e) {
            System.err.println("Error searching students: " + e.getMessage());
        }
        return list;
    }

    public int getTotalStudents() {
        String sql = "SELECT COUNT(*) FROM students WHERE status='Active'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    private Student mapStudent(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setId(rs.getInt("id"));
        s.setStudentId(rs.getString("student_id"));
        s.setFullName(rs.getString("full_name"));
        s.setEmail(rs.getString("email"));
        s.setPhone(rs.getString("phone"));
        s.setAddress(rs.getString("address"));
        s.setGuardianName(rs.getString("guardian_name"));
        s.setGuardianPhone(rs.getString("guardian_phone"));
        s.setCourse(rs.getString("course"));
        s.setAdmissionDate(rs.getString("admission_date"));
        s.setStatus(rs.getString("status"));
        return s;
    }
}
