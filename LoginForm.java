package hostel.management.system.ui;

import hostel.management.system.dao.AdminDAO;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class LoginForm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnExit;
    private AdminDAO adminDAO;

    public LoginForm() {
        adminDAO = new AdminDAO();
        initComponents();
    }

    private void initComponents() {
        setTitle("Smart Hostel Management System - Login");
        setSize(480, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(26, 35, 126),
                        0, getHeight(), new Color(13, 71, 161));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());

       
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(40, 30, 20, 30));

        JLabel iconLabel = new JLabel("🏠", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Smart Hostel", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Management System", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(200, 220, 255));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(iconLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitleLabel);

       
        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);

        
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(new Color(255, 255, 255, 230));
        cardPanel.setBorder(new RoundedBorder(15));
        cardPanel.setLayout(new GridBagLayout());
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(15),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints cgbc = new GridBagConstraints();
        cgbc.fill = GridBagConstraints.HORIZONTAL;
        cgbc.insets = new Insets(8, 0, 8, 0);
        cgbc.gridx = 0; cgbc.gridy = 0; cgbc.gridwidth = 1;

        JLabel loginTitle = new JLabel("Admin Login");
        loginTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        loginTitle.setForeground(new Color(26, 35, 126));
        loginTitle.setHorizontalAlignment(SwingConstants.CENTER);
        cgbc.gridy = 0;
        cardPanel.add(loginTitle, cgbc);

        
        JLabel lblUser = new JLabel("Username");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblUser.setForeground(new Color(60, 60, 60));
        cgbc.gridy = 1;
        cardPanel.add(lblUser, cgbc);

        txtUsername = new JTextField(20);
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtUsername.setPreferredSize(new Dimension(280, 38));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        cgbc.gridy = 2;
        cardPanel.add(txtUsername, cgbc);

        
        JLabel lblPass = new JLabel("Password");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPass.setForeground(new Color(60, 60, 60));
        cgbc.gridy = 3;
        cardPanel.add(lblPass, cgbc);

        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtPassword.setPreferredSize(new Dimension(280, 38));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        cgbc.gridy = 4;
        cardPanel.add(txtPassword, cgbc);

       
        btnLogin = new JButton("LOGIN");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(new Color(26, 35, 126));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setPreferredSize(new Dimension(280, 42));
        btnLogin.setBorderPainted(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cgbc.gridy = 5;
        cgbc.insets = new Insets(20, 0, 8, 0);
        cardPanel.add(btnLogin, cgbc);

        
        btnExit = new JButton("EXIT");
        btnExit.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnExit.setBackground(new Color(220, 53, 69));
        btnExit.setForeground(Color.WHITE);
        btnExit.setPreferredSize(new Dimension(280, 36));
        btnExit.setBorderPainted(false);
        btnExit.setFocusPainted(false);
        btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cgbc.gridy = 6;
        cgbc.insets = new Insets(4, 0, 0, 0);
        cardPanel.add(btnExit, cgbc);

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(cardPanel, gbc);

        
        JLabel footer = new JLabel("© 2024 Smart Hostel Management System", SwingConstants.CENTER);
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        footer.setForeground(new Color(150, 180, 220));
        footer.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(footer, BorderLayout.SOUTH);

        add(mainPanel);

     
        btnLogin.addActionListener(e -> performLogin());
        btnExit.addActionListener(e -> System.exit(0));
        txtPassword.addActionListener(e -> performLogin());

       
        addHoverEffect(btnLogin, new Color(26, 35, 126), new Color(48, 63, 159));
        addHoverEffect(btnExit, new Color(220, 53, 69), new Color(200, 35, 51));
    }

    private void addHoverEffect(JButton btn, Color normal, Color hover) {
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(hover); }
            public void mouseExited(MouseEvent e) { btn.setBackground(normal); }
        });
    }

    private void performLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username এবং Password দিন!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        btnLogin.setText("Logging in...");
        btnLogin.setEnabled(false);

        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            protected Boolean doInBackground() { return adminDAO.validateLogin(username, password); }
            protected void done() {
                try {
                    if (get()) {
                        String adminName = adminDAO.getAdminName(username);
                        JOptionPane.showMessageDialog(LoginForm.this, "Welcome, " + adminName + "! ✓", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                        new DashboardForm(adminName).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(LoginForm.this, "Invalid Username or Password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                        txtPassword.setText("");
                        btnLogin.setText("LOGIN");
                        btnLogin.setEnabled(true);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(LoginForm.this, "Database connection error!\nCheck MySQL connection.", "Error", JOptionPane.ERROR_MESSAGE);
                    btnLogin.setText("LOGIN");
                    btnLogin.setEnabled(true);
                }
            }
        };
        worker.execute();
    }

  
    static class RoundedBorder extends AbstractBorder {
        private int radius;
        RoundedBorder(int radius) { this.radius = radius; }
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(200, 200, 200));
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
        public Insets getBorderInsets(Component c) { return new Insets(radius, radius, radius, radius); }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { e.printStackTrace(); }
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
