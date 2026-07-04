package gui;

import dao.UserDAO;
import model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class AdminLoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginBtn, switchBtn;
    private UserDAO userDAO;

    public AdminLoginFrame() {
        userDAO = new UserDAO();
        setTitle("Admin Dashboard Login");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main Background Panel
        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(new Color(245, 247, 250)); // Light modern background

        // Card Panel (White container centered)
        JPanel cardPanel = new JPanel(null);
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBounds(50, 40, 400, 380);
        cardPanel.setBorder(new LineBorder(new Color(230, 230, 230), 1, true));
        mainPanel.add(cardPanel);

        // Header
        JLabel header = new JLabel("Admin Access", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setForeground(new Color(33, 37, 41));
        header.setBounds(0, 30, 400, 40);
        cardPanel.add(header);

        JLabel subHeader = new JLabel("Sign in to your dashboard", SwingConstants.CENTER);
        subHeader.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subHeader.setForeground(new Color(108, 117, 125));
        subHeader.setBounds(0, 70, 400, 25);
        cardPanel.add(subHeader);

        // Username
        JLabel userLabel = new JLabel("Admin Username");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        userLabel.setForeground(new Color(73, 80, 87));
        userLabel.setBounds(50, 115, 150, 20);
        cardPanel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setBounds(50, 135, 300, 40);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(206, 212, 218), 1, true),
            new EmptyBorder(5, 10, 5, 10)));
        cardPanel.add(usernameField);

        // Password
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        passLabel.setForeground(new Color(73, 80, 87));
        passLabel.setBounds(50, 190, 100, 20);
        cardPanel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBounds(50, 210, 300, 40);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(206, 212, 218), 1, true),
            new EmptyBorder(5, 10, 5, 10)));
        cardPanel.add(passwordField);

        // Login Button
        loginBtn = new JButton("Login as Admin");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setBackground(new Color(220, 53, 69)); // Red accent for Admin
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setBounds(50, 270, 300, 40);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        loginBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { loginBtn.setBackground(new Color(200, 35, 51)); }
            public void mouseExited(MouseEvent e) { loginBtn.setBackground(new Color(220, 53, 69)); }
        });
        cardPanel.add(loginBtn);

        // Switch back to Customer Button
        switchBtn = new JButton("Back to Customer Login");
        switchBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        switchBtn.setBackground(Color.WHITE);
        switchBtn.setForeground(new Color(108, 117, 125));
        switchBtn.setFocusPainted(false);
        switchBtn.setBorder(new LineBorder(new Color(206, 212, 218), 1, true));
        switchBtn.setBounds(50, 320, 300, 35);
        switchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        switchBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { 
                switchBtn.setBackground(new Color(233, 236, 239));
            }
            public void mouseExited(MouseEvent e) { 
                switchBtn.setBackground(Color.WHITE);
            }
        });
        cardPanel.add(switchBtn);

        loginBtn.addActionListener(e -> performLogin());
        switchBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        setContentPane(mainPanel);
    }

    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = userDAO.authenticate(username, password);
        if (user != null) {
            if ("admin".equals(user.getRole())) {
                dispose();
                new AdminFrame(user).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Customer accounts cannot log in here.", "Access Denied", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid admin username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
