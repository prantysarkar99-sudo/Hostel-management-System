package hostel.management.system.model;

public class Room {
    private int id;
    private String roomNumber;
    private String roomType;
    private int capacity;
    private int currentOccupancy;
    private int floorNumber;
    private double monthlyFee;
    private String status;

    public Room() {}

    public Room(String roomNumber, String roomType, int capacity,
                int floorNumber, double monthlyFee, String status) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.capacity = capacity;
        this.currentOccupancy = 0;
        this.floorNumber = floorNumber;
        this.monthlyFee = monthlyFee;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public int getCurrentOccupancy() { return currentOccupancy; }
    public void setCurrentOccupancy(int currentOccupancy) { this.currentOccupancy = currentOccupancy; }
    public int getFloorNumber() { return floorNumber; }
    public void setFloorNumber(int floorNumber) { this.floorNumber = floorNumber; }
    public double getMonthlyFee() { return monthlyFee; }
    public void setMonthlyFee(double monthlyFee) { this.monthlyFee = monthlyFee; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
