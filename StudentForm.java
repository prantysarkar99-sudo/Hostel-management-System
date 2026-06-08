package hostel.management.system.ui;

import hostel.management.system.dao.StudentDAO;
import hostel.management.system.model.Student;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class StudentForm extends JFrame {
    private JTextField txtStudentId, txtName, txtEmail, txtPhone, txtAddress;
    private JTextField txtGuardianName, txtGuardianPhone, txtCourse, txtAdmissionDate;
    private JComboBox<String> cmbStatus;
    private JButton btnSave, btnClear, btnClose;
    private StudentDAO studentDAO = new StudentDAO();
    private Student editStudent;

    public StudentForm(Student student) {
        this.editStudent = student;
        initComponents();
        if (student != null) loadStudentData(student);
    }

    private void initComponents() {
        setTitle(editStudent == null ? "Add New Student" : "Edit Student");
        setSize(580, 620);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 242, 245));

   
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(26, 35, 126));
        header.setPreferredSize(new Dimension(580, 60));
        header.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JLabel title = new JLabel(editStudent == null ? "➕ Add New Student" : "✏️ Edit Student");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.WEST);

       
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 6);

        String[] labels = {"Student ID *", "Full Name *", "Email", "Phone", 
                           "Course", "Admission Date (YYYY-MM-DD)", "Guardian Name", "Guardian Phone", "Status"};

        txtStudentId = new JTextField(20);
        txtName = new JTextField(20);
        txtEmail = new JTextField(20);
        txtPhone = new JTextField(20);
        txtCourse = new JTextField(20);
        txtAdmissionDate = new JTextField(20);
        txtGuardianName = new JTextField(20);
        txtGuardianPhone = new JTextField(20);
        cmbStatus = new JComboBox<>(new String[]{"Active", "Inactive"});

        if (editStudent != null) txtStudentId.setEditable(false);

        Component[] fields = {txtStudentId, txtName, txtEmail, txtPhone, 
                               txtCourse, txtAdmissionDate, txtGuardianName, txtGuardianPhone, cmbStatus};

        for (int i = 0; i < labels.length; i++) {
            int row = i / 2;
            int col = (i % 2) * 2;

            gbc.gridx = col; gbc.gridy = row; gbc.gridwidth = 1; gbc.weightx = 0.3;
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lbl.setForeground(new Color(60, 60, 80));
            formPanel.add(lbl, gbc);

            gbc.gridx = col + 1; gbc.weightx = 0.7;
            styleField(fields[i]);
            formPanel.add(fields[i], gbc);
        }

     
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1; gbc.weightx = 0.3;
        JLabel lblAddr = new JLabel("Address");
        lblAddr.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblAddr.setForeground(new Color(60, 60, 80));
        formPanel.add(lblAddr, gbc);

        txtAddress = new JTextField(30);
        styleField(txtAddress);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 1.0;
        formPanel.add(txtAddress, gbc);

        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        btnPanel.setBackground(new Color(240, 242, 245));

        btnSave = createButton(editStudent == null ? "💾 Save Student" : "💾 Update Student", new Color(26, 35, 126));
        btnClear = createButton("🔄 Clear", new Color(100, 100, 100));
        btnClose = createButton("✖ Close", new Color(183, 28, 28));

        btnPanel.add(btnSave);
        btnPanel.add(btnClear);
        btnPanel.add(btnClose);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(formPanel), BorderLayout.CENTER);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        add(mainPanel);

      
        btnSave.addActionListener(e -> saveStudent());
        btnClear.addActionListener(e -> clearForm());
        btnClose.addActionListener(e -> dispose());
    }

    private void styleField(Component comp) {
        if (comp instanceof JTextField) {
            JTextField tf = (JTextField) comp;
            tf.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            tf.setPreferredSize(new Dimension(180, 32));
            tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
            ));
        }
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(160, 36));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void loadStudentData(Student s) {
        txtStudentId.setText(s.getStudentId());
        txtName.setText(s.getFullName());
        txtEmail.setText(s.getEmail());
        txtPhone.setText(s.getPhone());
        txtAddress.setText(s.getAddress());
        txtGuardianName.setText(s.getGuardianName());
        txtGuardianPhone.setText(s.getGuardianPhone());
        txtCourse.setText(s.getCourse());
        txtAdmissionDate.setText(s.getAdmissionDate());
        cmbStatus.setSelectedItem(s.getStatus());
    }

    private void saveStudent() {
        if (txtStudentId.getText().trim().isEmpty() || txtName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Student ID এবং Name আবশ্যক!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Student s = new Student();
        s.setStudentId(txtStudentId.getText().trim());
        s.setFullName(txtName.getText().trim());
        s.setEmail(txtEmail.getText().trim());
        s.setPhone(txtPhone.getText().trim());
        s.setAddress(txtAddress.getText().trim());
        s.setGuardianName(txtGuardianName.getText().trim());
        s.setGuardianPhone(txtGuardianPhone.getText().trim());
        s.setCourse(txtCourse.getText().trim());
        s.setAdmissionDate(txtAdmissionDate.getText().trim());
        s.setStatus(cmbStatus.getSelectedItem().toString());

        boolean result;
        if (editStudent == null) {
            result = studentDAO.addStudent(s);
            if (result) JOptionPane.showMessageDialog(this, "✅ Student সফলভাবে যোগ করা হয়েছে!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            result = studentDAO.updateStudent(s);
            if (result) JOptionPane.showMessageDialog(this, "✅ Student তথ্য আপডেট হয়েছে!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }

        if (!result) JOptionPane.showMessageDialog(this, "❌ Operation failed! Student ID আবার পরীক্ষা করুন।", "Error", JOptionPane.ERROR_MESSAGE);
        else dispose();
    }

    private void clearForm() {
        txtStudentId.setText(""); txtName.setText(""); txtEmail.setText("");
        txtPhone.setText(""); txtAddress.setText(""); txtGuardianName.setText("");
        txtGuardianPhone.setText(""); txtCourse.setText(""); txtAdmissionDate.setText("");
        cmbStatus.setSelectedIndex(0);
        txtStudentId.requestFocus();
    }
}
