package hostel.management.system.ui;

import hostel.management.system.util.DatabaseConnection;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");
        } catch (Exception e) {
            e.printStackTrace();
        }

       
        SwingUtilities.invokeLater(() -> {
            if (!DatabaseConnection.testConnection()) {
                JOptionPane.showMessageDialog(null,
                    "⚠️ Database Connection Failed!\n\n" +
                    "Please ensure:\n" +
                    "1. MySQL Server is running\n" +
                    "2. Database 'hostel_db' exists\n" +
                    "3. Credentials are correct in DatabaseConnection.java\n" +
                    "4. MySQL Connector JAR is in classpath",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            new LoginForm().setVisible(true);
        });
    }
}
