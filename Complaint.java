package hostel.management.system.model;

public class Complaint {
    private int id;
    private String complaintId;
    private String studentId;
    private String subject;
    private String description;
    private String category;
    private String status;
    private String submittedDate;
    private String resolvedDate;

    public Complaint() {}

    public Complaint(String complaintId, String studentId, String subject,
                     String description, String category, String status) {
        this.complaintId = complaintId;
        this.studentId = studentId;
        this.subject = subject;
        this.description = description;
        this.category = category;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getComplaintId() { return complaintId; }
    public void setComplaintId(String complaintId) { this.complaintId = complaintId; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getSubmittedDate() { return submittedDate; }
    public void setSubmittedDate(String submittedDate) { this.submittedDate = submittedDate; }
    public String getResolvedDate() { return resolvedDate; }
    public void setResolvedDate(String resolvedDate) { this.resolvedDate = resolvedDate; }
}
