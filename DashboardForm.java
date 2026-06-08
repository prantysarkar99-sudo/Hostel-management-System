package hostel.management.system.ui;

import hostel.management.system.dao.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class DashboardForm extends JFrame {
    private String adminName;
    private StudentDAO studentDAO = new StudentDAO();
    private RoomDAO roomDAO = new RoomDAO();
    private PaymentDAO paymentDAO = new PaymentDAO();
    private ComplaintDAO complaintDAO = new ComplaintDAO();

    
    private JLabel lblStudentCount, lblRoomCount, lblAvailRooms, lblComplaintCount;

    public DashboardForm(String adminName) {
        this.adminName = adminName;
        initComponents();
        loadStats();
    }

    private void initComponents() {
        setTitle("Smart Hostel Management System - Dashboard");
        setSize(1100, 680);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 242, 245));

        
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(26, 35, 126));
        sidebar.setPreferredSize(new Dimension(220, 680));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

      
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(20, 27, 100));
        logoPanel.setMaximumSize(new Dimension(220, 90));
        logoPanel.setMinimumSize(new Dimension(220, 90));
        logoPanel.setPreferredSize(new Dimension(220, 90));
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel logoIcon = new JLabel("🏠 Hostel MS");
        logoIcon.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
        logoIcon.setForeground(Color.WHITE);
        logoIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel adminLbl = new JLabel("Admin: " + adminName);
        adminLbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        adminLbl.setForeground(new Color(180, 200, 255));
        adminLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        logoPanel.add(Box.createVerticalGlue());
        logoPanel.add(logoIcon);
        logoPanel.add(Box.createVerticalStrut(5));
        logoPanel.add(adminLbl);
        logoPanel.add(Box.createVerticalGlue());

        sidebar.add(logoPanel);
        sidebar.add(Box.createVerticalStrut(10));

        
        String[][] navItems = {
            {"🏠", "Dashboard"},
            {"👤", "Student Management"},
            {"🚪", "Room Management"},
            {"💰", "Fee Management"},
            {"📢", "Complaints"},
            {"🚪", "Logout"}
        };

        for (String[] item : navItems) {
            JButton btn = createNavButton(item[0] + "  " + item[1]);
            sidebar.add(btn);
            sidebar.add(Box.createVerticalStrut(4));

            btn.addActionListener(e -> handleNavClick(item[1]));
        }

        sidebar.add(Box.createVerticalGlue());

        
        JPanel contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(new Color(240, 242, 245));

        
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setPreferredSize(new Dimension(880, 60));
        topBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        JLabel pageTitle = new JLabel("Dashboard Overview");
        pageTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        pageTitle.setForeground(new Color(40, 40, 40));

        JLabel dateLabel = new JLabel(new java.util.Date().toString().substring(0, 24));
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        dateLabel.setForeground(new Color(120, 120, 120));

        topBar.add(pageTitle, BorderLayout.WEST);
        topBar.add(dateLabel, BorderLayout.EAST);

       
        JPanel dashContent = new JPanel();
        dashContent.setBackground(new Color(240, 242, 245));
        dashContent.setLayout(new BorderLayout());
        dashContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setOpaque(false);

        lblStudentCount = new JLabel("0");
        lblRoomCount = new JLabel("0");
        lblAvailRooms = new JLabel("0");
        lblComplaintCount = new JLabel("0");

        statsPanel.add(createStatCard("Total Students", lblStudentCount, new Color(26, 35, 126), "👤"));
        statsPanel.add(createStatCard("Total Rooms", lblRoomCount, new Color(0, 121, 107), "🚪"));
        statsPanel.add(createStatCard("Available Rooms", lblAvailRooms, new Color(230, 81, 0), "✅"));
        statsPanel.add(createStatCard("Pending Complaints", lblComplaintCount, new Color(183, 28, 28), "📢"));

       
        JPanel quickPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        quickPanel.setOpaque(false);
        quickPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        String[][] quickItems = {
            {"➕ Add Student", "#1a237e"},
            {"🔍 Search Student", "#00695c"},
            {"🚪 Add Room", "#e65100"},
            {"💳 Record Payment", "#4a148c"},
            {"📋 View Complaints", "#880e4f"},
            {"📊 Payment Report", "#1b5e20"}
        };

        for (String[] qi : quickItems) {
            quickPanel.add(createQuickButton(qi[0], Color.decode(qi[1])));
        }

        
        Component[] qbtns = quickPanel.getComponents();
        ((JButton)qbtns[0]).addActionListener(e -> openStudentForm(null));
        ((JButton)qbtns[1]).addActionListener(e -> new StudentManagementForm().setVisible(true));
        ((JButton)qbtns[2]).addActionListener(e -> new RoomManagementForm().setVisible(true));
        ((JButton)qbtns[3]).addActionListener(e -> new PaymentForm().setVisible(true));
        ((JButton)qbtns[4]).addActionListener(e -> new ComplaintForm().setVisible(true));
        ((JButton)qbtns[5]).addActionListener(e -> new PaymentForm().setVisible(true));

        dashContent.add(statsPanel, BorderLayout.NORTH);
        dashContent.add(quickPanel, BorderLayout.CENTER);

        contentArea.add(topBar, BorderLayout.NORTH);
        contentArea.add(dashContent, BorderLayout.CENTER);

        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentArea, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void loadStats() {
        SwingWorker<int[], Void> worker = new SwingWorker<>() {
            protected int[] doInBackground() {
                return new int[]{
                    studentDAO.getTotalStudents(),
                    roomDAO.getTotalRooms(),
                    roomDAO.getAvailableRoomCount(),
                    complaintDAO.getPendingComplaints()
                };
            }
            protected void done() {
                try {
                    int[] stats = get();
                    lblStudentCount.setText(String.valueOf(stats[0]));
                    lblRoomCount.setText(String.valueOf(stats[1]));
                    lblAvailRooms.setText(String.valueOf(stats[2]));
                    lblComplaintCount.setText(String.valueOf(stats[3]));
                } catch (Exception e) { e.printStackTrace(); }
            }
        };
        worker.execute();
    }

    private JPanel createStatCard(String title, JLabel valueLabel, Color color, String icon) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel iconLbl = new JLabel(icon);
        iconLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        iconLbl.setHorizontalAlignment(SwingConstants.CENTER);

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(color);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel titleLbl = new JLabel(title, SwingConstants.CENTER);
        titleLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLbl.setForeground(new Color(100, 100, 100));

        JPanel center = new JPanel(new GridLayout(2, 1, 0, 5));
        center.setOpaque(false);
        center.add(valueLabel);
        center.add(titleLbl);

       
        JPanel topBar = new JPanel();
        topBar.setBackground(color);
        topBar.setPreferredSize(new Dimension(0, 4));

        card.add(topBar, BorderLayout.NORTH);
        card.add(iconLbl, BorderLayout.WEST);
        card.add(center, BorderLayout.CENTER);

        return card;
    }

    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(new Color(200, 220, 255));
        btn.setBackground(new Color(26, 35, 126));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(220, 42));
        btn.setMinimumSize(new Dimension(220, 42));
        btn.setPreferredSize(new Dimension(220, 42));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 8));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(48, 63, 159)); }
            public void mouseExited(MouseEvent e) { btn.setBackground(new Color(26, 35, 126)); }
        });
        return btn;
    }

    private JButton createQuickButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(color.darker()); }
            public void mouseExited(MouseEvent e) { btn.setBackground(color); }
        });
        return btn;
    }

    private void handleNavClick(String section) {
        switch (section) {
            case "Student Management": new StudentManagementForm().setVisible(true); break;
            case "Room Management": new RoomManagementForm().setVisible(true); break;
            case "Fee Management": new PaymentForm().setVisible(true); break;
            case "Complaints": new ComplaintForm().setVisible(true); break;
            case "Logout":
                int c = JOptionPane.showConfirmDialog(this, "Logout করবেন?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (c == JOptionPane.YES_OPTION) { new LoginForm().setVisible(true); dispose(); }
                break;
        }
    }

    private void openStudentForm(Object o) {
        new StudentForm(null).setVisible(true);
    }
}
