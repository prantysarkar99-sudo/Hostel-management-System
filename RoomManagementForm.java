package hostel.management.system.ui;

import hostel.management.system.dao.RoomDAO;
import hostel.management.system.dao.StudentDAO;
import hostel.management.system.model.Room;
import hostel.management.system.model.Student;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class RoomManagementForm extends JFrame {
    private JTable roomTable;
    private DefaultTableModel tableModel;
    private JButton btnAddRoom, btnAllocate, btnRefresh, btnAvailable;
    private RoomDAO roomDAO = new RoomDAO();
    private StudentDAO studentDAO = new StudentDAO();

    public RoomManagementForm() {
        initComponents();
        loadRooms();
    }

    private void initComponents() {
        setTitle("Room Management");
        setSize(900, 580);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 242, 245));

        
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(0, 121, 107));
        header.setPreferredSize(new Dimension(900, 55));
        header.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        JLabel title = new JLabel("🚪 Room Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.WEST);

        
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        toolbar.setBackground(Color.WHITE);
        toolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));

        btnAddRoom = createBtn("➕ Add Room", new Color(26, 35, 126));
        btnAllocate = createBtn("🔑 Allocate Room", new Color(0, 121, 107));
        btnAvailable = createBtn("✅ Available Rooms", new Color(230, 81, 0));
        btnRefresh = createBtn("🔄 All Rooms", new Color(80, 80, 80));

        toolbar.add(btnAddRoom);
        toolbar.add(btnAllocate);
        toolbar.add(btnAvailable);
        toolbar.add(btnRefresh);

        
        String[] cols = {"Room No.", "Type", "Capacity", "Occupied", "Available Slots", "Floor", "Monthly Fee (৳)", "Status"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        roomTable = new JTable(tableModel);
        roomTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        roomTable.setRowHeight(30);
        roomTable.setSelectionBackground(new Color(200, 230, 210));
        roomTable.setGridColor(new Color(230, 230, 230));

        JTableHeader tableHeader = roomTable.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 12));
        tableHeader.setBackground(new Color(0, 121, 107));
        tableHeader.setForeground(Color.BLACK);

        int[] widths = {80, 90, 80, 80, 100, 60, 130, 90};
        for (int i = 0; i < widths.length; i++)
            roomTable.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

        
        roomTable.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean foc, int r, int c) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(t, val, sel, foc, r, c);
                lbl.setHorizontalAlignment(CENTER);
                if ("Available".equals(val)) { lbl.setForeground(new Color(0, 150, 0)); lbl.setFont(lbl.getFont().deriveFont(Font.BOLD)); }
                else if ("Full".equals(val)) { lbl.setForeground(new Color(200, 0, 0)); lbl.setFont(lbl.getFont().deriveFont(Font.BOLD)); }
                else { lbl.setForeground(new Color(150, 100, 0)); lbl.setFont(lbl.getFont().deriveFont(Font.BOLD)); }
                return lbl;
            }
        });

        JScrollPane scrollPane = new JScrollPane(roomTable);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(header, BorderLayout.NORTH);
        topPanel.add(toolbar, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        btnAddRoom.addActionListener(e -> showAddRoomDialog());
        btnAllocate.addActionListener(e -> showAllocateDialog());
        btnAvailable.addActionListener(e -> loadAvailableRooms());
        btnRefresh.addActionListener(e -> loadRooms());
    }

    private void loadRooms() {
        tableModel.setRowCount(0);
        for (Room r : roomDAO.getAllRooms()) {
            tableModel.addRow(new Object[]{
                r.getRoomNumber(), r.getRoomType(), r.getCapacity(),
                r.getCurrentOccupancy(), r.getCapacity() - r.getCurrentOccupancy(),
                r.getFloorNumber(), String.format("%.0f", r.getMonthlyFee()), r.getStatus()
            });
        }
    }

    private void loadAvailableRooms() {
        tableModel.setRowCount(0);
        for (Room r : roomDAO.getAvailableRooms()) {
            tableModel.addRow(new Object[]{
                r.getRoomNumber(), r.getRoomType(), r.getCapacity(),
                r.getCurrentOccupancy(), r.getCapacity() - r.getCurrentOccupancy(),
                r.getFloorNumber(), String.format("%.0f", r.getMonthlyFee()), r.getStatus()
            });
        }
    }

    private void showAddRoomDialog() {
        JDialog dialog = new JDialog(this, "Add New Room", true);
        dialog.setSize(400, 380);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(6, 2, 10, 12));
        form.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
        form.setBackground(Color.WHITE);

        JTextField txtRoomNo = new JTextField();
        JComboBox<String> cmbType = new JComboBox<>(new String[]{"Single", "Double", "Triple", "Dormitory"});
        JTextField txtCapacity = new JTextField();
        JTextField txtFloor = new JTextField();
        JTextField txtFee = new JTextField();
        JComboBox<String> cmbStatus = new JComboBox<>(new String[]{"Available", "Maintenance"});

        String[][] fields = {{"Room Number *", ""}, {"Room Type", ""}, {"Capacity *", ""}, {"Floor No.", ""}, {"Monthly Fee (৳)", ""}, {"Status", ""}};
        Component[] comps = {txtRoomNo, cmbType, txtCapacity, txtFloor, txtFee, cmbStatus};

        for (int i = 0; i < fields.length; i++) {
            JLabel lbl = new JLabel(fields[i][0]);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            form.add(lbl);
            form.add(comps[i]);
        }

        JButton saveBtn = new JButton("💾 Save Room");
        saveBtn.setBackground(new Color(26, 35, 126));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        saveBtn.setBorderPainted(false);
        saveBtn.setFocusPainted(false);

        saveBtn.addActionListener(e -> {
            try {
                Room room = new Room();
                room.setRoomNumber(txtRoomNo.getText().trim());
                room.setRoomType(cmbType.getSelectedItem().toString());
                room.setCapacity(Integer.parseInt(txtCapacity.getText().trim()));
                room.setFloorNumber(txtFloor.getText().isEmpty() ? 1 : Integer.parseInt(txtFloor.getText()));
                room.setMonthlyFee(txtFee.getText().isEmpty() ? 0 : Double.parseDouble(txtFee.getText()));
                room.setStatus(cmbStatus.getSelectedItem().toString());

                if (roomDAO.addRoom(room)) {
                    JOptionPane.showMessageDialog(dialog, "✅ Room সফলভাবে যোগ করা হয়েছে!");
                    loadRooms();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "❌ Room যোগ করা সম্ভব হয়নি!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "সঠিক সংখ্যা দিন!", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setBackground(new Color(240, 242, 245));
        btnPanel.add(saveBtn);

        dialog.add(form, BorderLayout.CENTER);
        dialog.add(btnPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showAllocateDialog() {
        JDialog dialog = new JDialog(this, "Allocate Room to Student", true);
        dialog.setSize(400, 260);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 15));
        form.setBorder(BorderFactory.createEmptyBorder(25, 30, 15, 30));
        form.setBackground(Color.WHITE);

        
        JLabel lblStudent = new JLabel("Student ID *");
        lblStudent.setFont(new Font("Segoe UI", Font.BOLD, 12));
        List<Student> students = studentDAO.getAllStudents();
        String[] studentIds = students.stream().map(s -> s.getStudentId() + " - " + s.getFullName()).toArray(String[]::new);
        JComboBox<String> cmbStudent = new JComboBox<>(studentIds);

       
        JLabel lblRoom = new JLabel("Room Number *");
        lblRoom.setFont(new Font("Segoe UI", Font.BOLD, 12));
        List<Room> availRooms = roomDAO.getAvailableRooms();
        String[] roomNos = availRooms.stream().map(r -> r.getRoomNumber() + " (" + r.getRoomType() + ")").toArray(String[]::new);
        JComboBox<String> cmbRoom = new JComboBox<>(roomNos);

        form.add(lblStudent); form.add(cmbStudent);
        form.add(lblRoom); form.add(cmbRoom);

        JButton allocBtn = new JButton("✅ Allocate Room");
        allocBtn.setBackground(new Color(0, 121, 107));
        allocBtn.setForeground(Color.WHITE);
        allocBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        allocBtn.setBorderPainted(false);
        allocBtn.setFocusPainted(false);

        allocBtn.addActionListener(e -> {
            if (cmbStudent.getSelectedItem() == null || cmbRoom.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(dialog, "Student এবং Room সিলেক্ট করুন!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String studentId = cmbStudent.getSelectedItem().toString().split(" - ")[0];
            String roomNo = cmbRoom.getSelectedItem().toString().split(" ")[0];

            if (roomDAO.allocateRoom(studentId, roomNo)) {
                JOptionPane.showMessageDialog(dialog, "✅ Room সফলভাবে Allocate করা হয়েছে!");
                loadRooms();
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "❌ Room Full অথবা Already Allocated!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setBackground(new Color(240, 242, 245));
        btnPanel.add(allocBtn);

        dialog.add(form, BorderLayout.CENTER);
        dialog.add(btnPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private JButton createBtn(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(140, 30));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
