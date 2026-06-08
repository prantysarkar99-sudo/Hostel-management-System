package hostel.management.system.ui;

import hostel.management.system.dao.StudentDAO;
import hostel.management.system.model.Student;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class StudentManagementForm extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    private JButton btnAdd, btnEdit, btnDelete, btnRefresh, btnSearch;
    private StudentDAO studentDAO = new StudentDAO();

    public StudentManagementForm() {
        initComponents();
        loadStudents();
    }

    private void initComponents() {
        setTitle("Student Management");
        setSize(950, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 242, 245));

       
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(26, 35, 126));
        header.setPreferredSize(new Dimension(950, 55));
        header.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JLabel title = new JLabel("👤 Student Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.WEST);

        
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        toolbar.setBackground(Color.WHITE);
        toolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));

        btnAdd = createBtn(" Add Student", new Color(26, 35, 126));
        btnEdit = createBtn("️ Edit", new Color(0, 121, 107));
        btnDelete = createBtn("️ Delete", new Color(183, 28, 28));
        btnRefresh = createBtn(" Refresh", new Color(80, 80, 80));

        JLabel searchLbl = new JLabel(" Search:");
        searchLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtSearch = new JTextField(20);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtSearch.setPreferredSize(new Dimension(180, 30));
        btnSearch = createBtn("Search", new Color(230, 81, 0));

        toolbar.add(btnAdd);
        toolbar.add(btnEdit);
        toolbar.add(btnDelete);
        toolbar.add(btnRefresh);
        toolbar.add(Box.createHorizontalStrut(20));
        toolbar.add(searchLbl);
        toolbar.add(txtSearch);
        toolbar.add(btnSearch);

   
        String[] columns = {"Student ID", "Full Name", "Email", "Phone", "Course", "Admission Date", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        
        

        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.setSelectionBackground(new Color(200, 220, 255));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(230, 230, 230));
        table.setShowGrid(true);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 12));
        tableHeader.setBackground(new Color(26, 35, 126));
        tableHeader.setForeground(Color.BLACK);

        
        int[] widths = {90, 150, 180, 110, 150, 120, 80};
        for (int i = 0; i < widths.length; i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

      
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBackground(new Color(240, 240, 240));
        statusBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));
        JLabel statusLbl = new JLabel("Total students: loading...");
        statusLbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        statusBar.add(statusLbl);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(toolbar, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(statusBar, BorderLayout.SOUTH);

       
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(header, BorderLayout.NORTH);
        topPanel.add(toolbar, BorderLayout.SOUTH);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        add(mainPanel);

   
        btnAdd.addActionListener(e -> { new StudentForm(null).setVisible(true); loadStudents(); });
        btnEdit.addActionListener(e -> editStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnRefresh.addActionListener(e -> loadStudents());
        btnSearch.addActionListener(e -> searchStudents());
        txtSearch.addActionListener(e -> searchStudents());
    }

    private void loadStudents() {
        tableModel.setRowCount(0);
        List<Student> students = studentDAO.getAllStudents();
        for (Student s : students) {
            tableModel.addRow(new Object[]{
                s.getStudentId(), s.getFullName(), s.getEmail(), s.getPhone(),
                s.getCourse(), s.getAdmissionDate(), s.getStatus()
            });
        }
    }

    private void searchStudents() {
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()) { loadStudents(); return; }

        tableModel.setRowCount(0);
        List<Student> results = studentDAO.searchStudents(keyword);
        for (Student s : results) {
            tableModel.addRow(new Object[]{
                s.getStudentId(), s.getFullName(), s.getEmail(), s.getPhone(),
                s.getCourse(), s.getAdmissionDate(), s.getStatus()
            });
        }

        if (results.isEmpty()) JOptionPane.showMessageDialog(this, "কোনো Student পাওয়া যায়নি!", "Search Result", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editStudent() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Edit করতে একটি Student সিলেক্ট করুন!", "Warning", JOptionPane.WARNING_MESSAGE); return; }

        String id = tableModel.getValueAt(row, 0).toString();
        Student s = studentDAO.getStudentById(id);
        if (s != null) {
            StudentForm form = new StudentForm(s);
            form.setVisible(true);
            form.addWindowListener(new WindowAdapter() {
                public void windowClosed(WindowEvent e) { loadStudents(); }
            });
        }
    }

    private void deleteStudent() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Delete করতে একটি Student সিলেক্ট করুন!", "Warning", JOptionPane.WARNING_MESSAGE); return; }

        String id = tableModel.getValueAt(row, 0).toString();
        String name = tableModel.getValueAt(row, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
            "আপনি কি " + name + " কে Delete করতে চান?",
            "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (studentDAO.deleteStudent(id)) {
                JOptionPane.showMessageDialog(this, "✅ Student সফলভাবে ডিলিট হয়েছে!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadStudents();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Delete করা সম্ভব হয়নি! Payment বা Room allocation থাকতে পারে।", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JButton createBtn(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(120, 30));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
