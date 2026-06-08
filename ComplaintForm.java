package hostel.management.system.ui;

import hostel.management.system.dao.ComplaintDAO;
import hostel.management.system.dao.StudentDAO;
import hostel.management.system.model.Complaint;
import hostel.management.system.model.Student;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ComplaintForm extends JFrame {
    private JTable complaintTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> cmbStudent, cmbCategory;
    private JTextField txtSubject;
    private JTextArea txtDescription;
    private JButton btnSubmit, btnClear, btnUpdateStatus, btnRefresh;
    private ComplaintDAO complaintDAO = new ComplaintDAO();
    private StudentDAO studentDAO = new StudentDAO();

    public ComplaintForm() {
        initComponents();
        loadComplaints();
    }

    private void initComponents() {
        setTitle("Complaint Management");
        setSize(1000, 650);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 242, 245));

        
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(136, 14, 79));
        header.setPreferredSize(new Dimension(1000, 55));
        header.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        JLabel title = new JLabel("📢 Complaint Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.WEST);

        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(360);
        splitPane.setDividerSize(3);

      
        JPanel submitPanel = new JPanel(new BorderLayout());
        submitPanel.setBackground(Color.WHITE);

        JLabel formTitle = new JLabel("  Submit Complaint");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formTitle.setForeground(new Color(136, 14, 79));
        formTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));
        formTitle.setPreferredSize(new Dimension(360, 40));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 4, 8, 4);

        List<Student> students = studentDAO.getAllStudents();
        String[] stuIds = students.stream().map(s -> s.getStudentId() + " - " + s.getFullName()).toArray(String[]::new);
        cmbStudent = new JComboBox<>(stuIds.length > 0 ? stuIds : new String[]{"No students"});
        cmbCategory = new JComboBox<>(new String[]{"Maintenance", "Cleanliness", "Food", "Security", "Other"});
        txtSubject = new JTextField();
        txtSubject.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        txtSubject.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));

        txtDescription = new JTextArea(4, 20);
        txtDescription.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        JLabel lblStu = new JLabel("Student *"); lblStu.setFont(new Font("Segoe UI", Font.BOLD, 11));
        form.add(lblStu, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        form.add(cmbStudent, gbc);

       
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        JLabel lblCat = new JLabel("Category *"); lblCat.setFont(new Font("Segoe UI", Font.BOLD, 11));
        form.add(lblCat, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        form.add(cmbCategory, gbc);

        
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        JLabel lblSub = new JLabel("Subject *"); lblSub.setFont(new Font("Segoe UI", Font.BOLD, 11));
        form.add(lblSub, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        form.add(txtSubject, gbc);

        
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.3;
        JLabel lblDesc = new JLabel("Description"); lblDesc.setFont(new Font("Segoe UI", Font.BOLD, 11));
        form.add(lblDesc, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        form.add(new JScrollPane(txtDescription), gbc);

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.setBackground(Color.WHITE);

        btnSubmit = new JButton("📤 Submit Complaint");
        btnSubmit.setBackground(new Color(136, 14, 79));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSubmit.setBorderPainted(false);
        btnSubmit.setFocusPainted(false);
        btnSubmit.setPreferredSize(new Dimension(170, 34));

        btnClear = new JButton("🔄 Clear");
        btnClear.setBackground(new Color(100, 100, 100));
        btnClear.setForeground(Color.WHITE);
        btnClear.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnClear.setBorderPainted(false);
        btnClear.setFocusPainted(false);
        btnClear.setPreferredSize(new Dimension(90, 34));

        btnPanel.add(btnSubmit);
        btnPanel.add(btnClear);

        submitPanel.add(formTitle, BorderLayout.NORTH);
        submitPanel.add(form, BorderLayout.CENTER);
        submitPanel.add(btnPanel, BorderLayout.SOUTH);

        
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBackground(new Color(240, 242, 245));

        JPanel histToolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        histToolbar.setBackground(Color.WHITE);

        btnUpdateStatus = new JButton("✏️ Update Status");
        btnUpdateStatus.setBackground(new Color(0, 121, 107));
        btnUpdateStatus.setForeground(Color.WHITE);
        btnUpdateStatus.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnUpdateStatus.setBorderPainted(false);
        btnUpdateStatus.setFocusPainted(false);

        btnRefresh = new JButton("🔄 Refresh");
        btnRefresh.setBackground(new Color(80, 80, 80));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnRefresh.setBorderPainted(false);
        btnRefresh.setFocusPainted(false);

        histToolbar.add(new JLabel("All Complaints:") {{ setFont(new Font("Segoe UI", Font.BOLD, 12)); }});
        histToolbar.add(btnUpdateStatus);
        histToolbar.add(btnRefresh);

        String[] cols = {"Complaint ID", "Student ID", "Category", "Subject", "Status", "Date"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        complaintTable = new JTable(tableModel);
        complaintTable.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        complaintTable.setRowHeight(28);
        complaintTable.setSelectionBackground(new Color(255, 200, 230));
        complaintTable.setGridColor(new Color(230, 230, 230));

        JTableHeader th = complaintTable.getTableHeader();
        th.setFont(new Font("Segoe UI", Font.BOLD, 11));
        th.setBackground(new Color(136, 14, 79));
        th.setForeground(Color.BLACK);

       
        complaintTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean foc, int r, int c) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(t, val, sel, foc, r, c);
                lbl.setHorizontalAlignment(CENTER);
                if ("Pending".equals(val)) { lbl.setForeground(Color.RED); lbl.setFont(lbl.getFont().deriveFont(Font.BOLD)); }
                else if ("In Progress".equals(val)) { lbl.setForeground(new Color(200, 100, 0)); lbl.setFont(lbl.getFont().deriveFont(Font.BOLD)); }
                else { lbl.setForeground(new Color(0, 150, 0)); lbl.setFont(lbl.getFont().deriveFont(Font.BOLD)); }
                return lbl;
            }
        });

        historyPanel.add(histToolbar, BorderLayout.NORTH);
        historyPanel.add(new JScrollPane(complaintTable), BorderLayout.CENTER);

        splitPane.setLeftComponent(submitPanel);
        splitPane.setRightComponent(historyPanel);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        add(mainPanel);

        
        btnSubmit.addActionListener(e -> submitComplaint());
        btnClear.addActionListener(e -> clearForm());
        btnUpdateStatus.addActionListener(e -> updateStatus());
        btnRefresh.addActionListener(e -> loadComplaints());
    }

    private void loadComplaints() {
        tableModel.setRowCount(0);
        for (Complaint c : complaintDAO.getAllComplaints()) {
            String dateStr = c.getSubmittedDate() != null ? c.getSubmittedDate().substring(0, 10) : "";
            tableModel.addRow(new Object[]{
                c.getComplaintId(), c.getStudentId(),
                c.getCategory(), c.getSubject(),
                c.getStatus(), dateStr
            });
        }
    }

    private void submitComplaint() {
        if (cmbStudent.getSelectedItem() == null || txtSubject.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Student এবং Subject আবশ্যক!", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String studentId = cmbStudent.getSelectedItem().toString().split(" - ")[0];
        Complaint c = new Complaint();
        c.setComplaintId(complaintDAO.generateComplaintId());
        c.setStudentId(studentId);
        c.setSubject(txtSubject.getText().trim());
        c.setDescription(txtDescription.getText().trim());
        c.setCategory(cmbCategory.getSelectedItem().toString());
        c.setStatus("Pending");

        if (complaintDAO.addComplaint(c)) {
            JOptionPane.showMessageDialog(this, "omplaint সফলভাবে Submit হয়েছে!\nID: " + c.getComplaintId(), "Success", JOptionPane.INFORMATION_MESSAGE);
            loadComplaints();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, " Complaint submit করা সম্ভব হয়নি!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStatus() {
        int row = complaintTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, " Complaint ", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cId = tableModel.getValueAt(row, 0).toString();
        String[] statuses = {"Pending", "In Progress", "Resolved"};
        String newStatus = (String) JOptionPane.showInputDialog(this,
            "নতুন Status সিলেক্ট করুন:", "Update Status",
            JOptionPane.QUESTION_MESSAGE, null, statuses, statuses[0]);

        if (newStatus != null) {
            if (complaintDAO.updateComplaintStatus(cId, newStatus)) {
                JOptionPane.showMessageDialog(this, "✅ Status আপডেট হয়েছে!");
                loadComplaints();
            }
        }
    }

    private void clearForm() {
        txtSubject.setText("");
        txtDescription.setText("");
        cmbCategory.setSelectedIndex(0);
    }
}
