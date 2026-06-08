package hostel.management.system.model;

public class Payment {
    private int id;
    private String paymentId;
    private String studentId;
    private double amount;
    private String paymentType;
    private String paymentMethod;
    private String paymentDate;
    private String monthYear;
    private String description;

    public Payment() {}

    public Payment(String paymentId, String studentId, double amount,
                   String paymentType, String paymentMethod,
                   String paymentDate, String monthYear, String description) {
        this.paymentId = paymentId;
        this.studentId = studentId;
        this.amount = amount;
        this.paymentType = paymentType;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.monthYear = monthYear;
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getPaymentType() { return paymentType; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getPaymentDate() { return paymentDate; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }
    public String getMonthYear() { return monthYear; }
    public void setMonthYear(String monthYear) { this.monthYear = monthYear; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
