package hostel.management.system.dao;

import hostel.management.system.model.Room;
import hostel.management.system.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    
    public boolean addRoom(Room room) {
        String sql = "INSERT INTO rooms (room_number, room_type, capacity, floor_number, monthly_fee, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, room.getRoomNumber());
            ps.setString(2, room.getRoomType());
            ps.setInt(3, room.getCapacity());
            ps.setInt(4, room.getFloorNumber());
            ps.setDouble(5, room.getMonthlyFee());
            ps.setString(6, room.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding room: " + e.getMessage());
            return false;
        }
    }

    
    public boolean allocateRoom(String studentId, String roomNumber) {
        String checkSql = "SELECT capacity, current_occupancy FROM rooms WHERE room_number=?";
        String allocateSql = "INSERT INTO room_allocations (student_id, room_number, allocation_date, status) VALUES (?, ?, CURDATE(), 'Active')";
        String updateSql = "UPDATE rooms SET current_occupancy = current_occupancy + 1, " +
                           "status = CASE WHEN current_occupancy + 1 >= capacity THEN 'Full' ELSE 'Available' END " +
                           "WHERE room_number=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check availability
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setString(1, roomNumber);
            ResultSet rs = checkPs.executeQuery();
            if (rs.next()) {
                int capacity = rs.getInt("capacity");
                int occupancy = rs.getInt("current_occupancy");
                if (occupancy >= capacity) return false;
            }

            PreparedStatement allocPs = conn.prepareStatement(allocateSql);
            allocPs.setString(1, studentId);
            allocPs.setString(2, roomNumber);
            allocPs.executeUpdate();

            PreparedStatement updatePs = conn.prepareStatement(updateSql);
            updatePs.setString(1, roomNumber);
            updatePs.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error allocating room: " + e.getMessage());
            return false;
        }
    }

   
    public List<Room> getAllRooms() {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM rooms ORDER BY room_number";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRoom(rs));
        } catch (SQLException e) {
            System.err.println("Error fetching rooms: " + e.getMessage());
        }
        return list;
    }

    
    public List<Room> getAvailableRooms() {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE status='Available' ORDER BY room_number";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRoom(rs));
        } catch (SQLException e) {
            System.err.println("Error fetching available rooms: " + e.getMessage());
        }
        return list;
    }

    public int getTotalRooms() {
        String sql = "SELECT COUNT(*) FROM rooms";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public int getAvailableRoomCount() {
        String sql = "SELECT COUNT(*) FROM rooms WHERE status='Available'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    private Room mapRoom(ResultSet rs) throws SQLException {
        Room r = new Room();
        r.setId(rs.getInt("id"));
        r.setRoomNumber(rs.getString("room_number"));
        r.setRoomType(rs.getString("room_type"));
        r.setCapacity(rs.getInt("capacity"));
        r.setCurrentOccupancy(rs.getInt("current_occupancy"));
        r.setFloorNumber(rs.getInt("floor_number"));
        r.setMonthlyFee(rs.getDouble("monthly_fee"));
        r.setStatus(rs.getString("status"));
        return r;
    }
}
