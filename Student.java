package hostel.management.system.model;

import java.util.Date;

public class Student {
    private int id;
    private String studentId;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String guardianName;
    private String guardianPhone;
    private String course;
    private String admissionDate;
    private String status;

    public Student() {}

    public Student(String studentId, String fullName, String email, String phone,
                   String address, String guardianName, String guardianPhone,
                   String course, String admissionDate, String status) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.guardianName = guardianName;
        this.guardianPhone = guardianPhone;
        this.course = course;
        this.admissionDate = admissionDate;
        this.status = status;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getGuardianName() { return guardianName; }
    public void setGuardianName(String guardianName) { this.guardianName = guardianName; }
    public String getGuardianPhone() { return guardianPhone; }
    public void setGuardianPhone(String guardianPhone) { this.guardianPhone = guardianPhone; }
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    public String getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(String admissionDate) { this.admissionDate = admissionDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
