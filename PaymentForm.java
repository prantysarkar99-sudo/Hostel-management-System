package hostel.management.system.ui;

import hostel.management.system.dao.PaymentDAO;
import hostel.management.system.dao.StudentDAO;
import hostel.management.system.model.Payment;
import hostel.management.system.model.Student;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;

public class PaymentForm extends JFrame {
    private JTable paymentTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> cmbStudent, cmbType, cmbMethod;
    private JTextField txtAmount, txtDate, txtMonth, txtDesc, txtSearchStudent;
    private JButton btnRecord, btnClear, btnViewHistory, btnAllPayments;
    private PaymentDAO paymentDAO = new PaymentDAO();
    private StudentDAO studentDAO = new StudentDAO();

    public PaymentForm() {
        initComponents();
        loadAllPayments();
    }

    private void initComponents() {
        setTitle("Fee Management");
        setSize(1000, 650);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 242, 245));

      
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(74, 20, 140));
        header.setPreferredSize(new Dimension(1000, 55));
        header.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        JLabel title = new JLabel("💰 Fee Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.WEST);

        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(360);
        splitPane.setDividerSize(3);

        
        JPanel entryPanel = new JPanel(new BorderLayout());
        entryPanel.setBackground(Color.WHITE);
        entryPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 220)));

        JLabel formTitle = new JLabel("  Record Payment");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formTitle.setForeground(new Color(74, 20, 140));
        formTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));
        formTitle.setPreferredSize(new Dimension(360, 40));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 4, 6, 4);
        gbc.gridwidth = 1;

        
        List<Student> students = studentDAO.getAllStudents();
        String[] stuIds = students.stream().map(s -> s.getStudentId() + " - " + s.getFullName()).toArray(String[]::new);
        cmbStudent = new JComboBox<>(stuIds.length > 0 ? stuIds : new String[]{"No students found"});
        cmbType = new JComboBox<>(new String[]{"Room Fee", "Mess Fee", "Other"});
        cmbMethod = new JComboBox<>(new String[]{"Cash", "Bank Transfer", "Online"});
        txtAmount = new JTextField();
        txtDate = new JTextField(LocalDate.now().toString());
        txtMonth = new JTextField(LocalDate.now().getYear() + "-" + String.format("%02d", LocalDate.now().getMonthValue()));
        txtDesc = new JTextField();

        String[] labels = {"Student *", "Payment Type", "Amount (৳) *", "Payment Method", "Payment Date *", "Month-Year", "Description"};
        Component[] comps = {cmbStudent, cmbType, txtAmount, cmbMethod, txtDate, txtMonth, txtDesc};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0.35;
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
            lbl.setForeground(new Color(60, 60, 80));
            form.add(lbl, gbc);

            gbc.gridx = 1; gbc.weightx = 0.65;
            if (comps[i] instanceof JTextField) {
                JTextField tf = (JTextField) comps[i];
                tf.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                tf.setPreferredSize(new Dimension(160, 28));
                tf.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(3, 6, 3, 6)
                ));
            }
            form.add(comps[i], gbc);
        }

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.setBackground(Color.WHITE);
        btnRecord = new JButton("💾 Record Payment");
        btnRecord.setBackground(new Color(74, 20, 140));
        btnRecord.setForeground(Color.WHITE);
        btnRecord.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRecord.setBorderPainted(false);
        btnRecord.setFocusPainted(false);
        btnRecord.setPreferredSize(new Dimension(160, 34));

        btnClear = new JButton("🔄 Clear");
        btnClear.setBackground(new Color(100, 100, 100));
        btnClear.setForeground(Color.WHITE);
        btnClear.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnClear.setBorderPainted(false);
        btnClear.setFocusPainted(false);
        btnClear.setPreferredSize(new Dimension(90, 34));

        btnPanel.add(btnRecord);
        btnPanel.add(btnClear);

        entryPanel.add(formTitle, BorderLayout.NORTH);
        entryPanel.add(form, BorderLayout.CENTER);
        entryPanel.add(btnPanel, BorderLayout.SOUTH);

        
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBackground(new Color(240, 242, 245));

        JPanel histToolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        histToolbar.setBackground(Color.WHITE);
        btnViewHistory = new JButton("Student History");
        btnViewHistory.setBackground(new Color(0, 121, 107));
        btnViewHistory.setForeground(Color.WHITE);
        btnViewHistory.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnViewHistory.setBorderPainted(false);
        btnViewHistory.setFocusPainted(false);

        btnAllPayments = new JButton("🔄 All Payments");
        btnAllPayments.setBackground(new Color(80, 80, 80));
        btnAllPayments.setForeground(Color.WHITE);
        btnAllPayments.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnAllPayments.setBorderPainted(false);
        btnAllPayments.setFocusPainted(false);

        txtSearchStudent = new JTextField(12);
        txtSearchStudent.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        txtSearchStudent.setToolTipText("Student ID দিন");
        JLabel searchLbl = new JLabel("Student ID:");
        searchLbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        histToolbar.add(searchLbl);
        histToolbar.add(txtSearchStudent);
        histToolbar.add(btnViewHistory);
        histToolbar.add(btnAllPayments);

        String[] cols = {"Payment ID", "Student ID", "Amount (৳)", "Type", "Method", "Date", "Month"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        paymentTable = new JTable(tableModel);
        paymentTable.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        paymentTable.setRowHeight(28);
        paymentTable.setSelectionBackground(new Color(220, 200, 255));
        paymentTable.setGridColor(new Color(230, 230, 230));

        JTableHeader th = paymentTable.getTableHeader();
        th.setFont(new Font("Segoe UI", Font.BOLD, 11));
        th.setBackground(new Color(74, 20, 140));
        th.setForeground(Color.BLACK);

        historyPanel.add(histToolbar, BorderLayout.NORTH);
        historyPanel.add(new JScrollPane(paymentTable), BorderLayout.CENTER);

        splitPane.setLeftComponent(entryPanel);
        splitPane.setRightComponent(historyPanel);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        add(mainPanel);

        
        btnRecord.addActionListener(e -> recordPayment());
        btnClear.addActionListener(e -> clearForm());
        btnViewHistory.addActionListener(e -> viewStudentHistory());
        btnAllPayments.addActionListener(e -> loadAllPayments());
    }

    private void loadAllPayments() {
        tableModel.setRowCount(0);
        for (Payment p : paymentDAO.getAllPayments()) {
            tableModel.addRow(new Object[]{
                p.getPaymentId(), p.getStudentId(),
                String.format("%.0f", p.getAmount()),
                p.getPaymentType(), p.getPaymentMethod(),
                p.getPaymentDate(), p.getMonthYear()
            });
        }
    }

    private void viewStudentHistory() {
        String sid = txtSearchStudent.getText().trim();
        if (sid.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Student ID !", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        tableModel.setRowCount(0);
        for (Payment p : paymentDAO.getPaymentsByStudent(sid)) {
            tableModel.addRow(new Object[]{
                p.getPaymentId(), p.getStudentId(),
                String.format("%.0f", p.getAmount()),
                p.getPaymentType(), p.getPaymentMethod(),
                p.getPaymentDate(), p.getMonthYear()
            });
        }
        if (tableModel.getRowCount() == 0)
            JOptionPane.showMessageDialog(this, "এই Student এর কোনো Payment পাওয়া যায়নি!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void recordPayment() {
        try {
            if (cmbStudent.getSelectedItem() == null || txtAmount.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Student এবং Amount আবশ্যক!", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String studentId = cmbStudent.getSelectedItem().toString().split(" - ")[0];
            double amount = Double.parseDouble(txtAmount.getText().trim());

            Payment p = new Payment();
            p.setPaymentId(paymentDAO.generatePaymentId());
            p.setStudentId(studentId);
            p.setAmount(amount);
            p.setPaymentType(cmbType.getSelectedItem().toString());
            p.setPaymentMethod(cmbMethod.getSelectedItem().toString());
            p.setPaymentDate(txtDate.getText().trim());
            p.setMonthYear(txtMonth.getText().trim());
            p.setDescription(txtDesc.getText().trim());

            if (paymentDAO.addPayment(p)) {
                JOptionPane.showMessageDialog(this, "Payment সফলভাবে রেকর্ড হয়েছে!\nPayment ID: " + p.getPaymentId(), "Success", JOptionPane.INFORMATION_MESSAGE);
                loadAllPayments();
                clearForm();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "সঠিক Amount দিন!", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        txtAmount.setText("");
        txtDesc.setText("");
        txtMonth.setText("");
    }
}
