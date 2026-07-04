package gui;

import dao.UserDAO;
import model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginBtn, registerBtn;
    private JButton customerTabBtn, adminTabBtn;
    private JLabel header, subHeader, loginBtnLabel;
    private UserDAO userDAO;
    private boolean isAdminMode = false;

    // Colors
    private static final Color BG_COLOR       = new Color(245, 247, 250);
    private static final Color CARD_COLOR      = Color.WHITE;
    private static final Color BLUE            = new Color(13, 110, 253);
    private static final Color BLUE_DARK       = new Color(11, 94, 215);
    private static final Color RED             = new Color(220, 53, 69);
    private static final Color RED_DARK        = new Color(200, 35, 51);
    private static final Color TEXT_DARK       = new Color(33, 37, 41);
    private static final Color TEXT_MUTED      = new Color(108, 117, 125);
    private static final Color TEXT_LABEL      = new Color(73, 80, 87);
    private static final Color BORDER_COLOR    = new Color(206, 212, 218);
    private static final Color CARD_BORDER     = new Color(230, 230, 230);

    public LoginFrame() {
        userDAO = new UserDAO();
        setTitle("Online Shopping System - Login");
        setSize(520, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // ── Main Background ──────────────────────────────────────────────
        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(BG_COLOR);

        // ── Card ─────────────────────────────────────────────────────────
        JPanel cardPanel = new JPanel(null);
        cardPanel.setBackground(CARD_COLOR);
        cardPanel.setBounds(50, 30, 420, 510);
        cardPanel.setBorder(new LineBorder(CARD_BORDER, 1, true));
        mainPanel.add(cardPanel);

        // ── Tab Bar ───────────────────────────────────────────────────────
        JPanel tabBar = new JPanel(new GridLayout(1, 2));
        tabBar.setBounds(0, 0, 420, 46);
        tabBar.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, CARD_BORDER));
        cardPanel.add(tabBar);

        customerTabBtn = makeTabButton("Customer Login", true);
        adminTabBtn    = makeTabButton("Admin Login", false);
        tabBar.add(customerTabBtn);
        tabBar.add(adminTabBtn);

        // ── Header ────────────────────────────────────────────────────────
        header = new JLabel("Customer Login", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(TEXT_DARK);
        header.setBounds(0, 60, 420, 36);
        cardPanel.add(header);

        subHeader = new JLabel("Sign in to your account", SwingConstants.CENTER);
        subHeader.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subHeader.setForeground(TEXT_MUTED);
        subHeader.setBounds(0, 96, 420, 22);
        cardPanel.add(subHeader);

        // ── Username ──────────────────────────────────────────────────────
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        userLabel.setForeground(TEXT_LABEL);
        userLabel.setBounds(60, 138, 120, 20);
        cardPanel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setBounds(60, 158, 300, 40);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(5, 10, 5, 10)));
        cardPanel.add(usernameField);

        // ── Password ──────────────────────────────────────────────────────
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        passLabel.setForeground(TEXT_LABEL);
        passLabel.setBounds(60, 212, 120, 20);
        cardPanel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBounds(60, 232, 300, 40);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(5, 10, 5, 10)));
        cardPanel.add(passwordField);

        // ── Login Button ─────────────────────────────────────────────────
        loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setBackground(BLUE);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setBounds(60, 295, 300, 42);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { loginBtn.setBackground(isAdminMode ? RED_DARK : BLUE_DARK); }
            public void mouseExited(MouseEvent e)  { loginBtn.setBackground(isAdminMode ? RED : BLUE); }
        });
        cardPanel.add(loginBtn);

        // ── Register Button (Customer only) ───────────────────────────────
        registerBtn = new JButton("Create New Account");
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        registerBtn.setBackground(CARD_COLOR);
        registerBtn.setForeground(BLUE);
        registerBtn.setFocusPainted(false);
        registerBtn.setBorder(new LineBorder(BLUE, 1, true));
        registerBtn.setBounds(60, 350, 300, 38);
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                registerBtn.setBackground(BLUE);
                registerBtn.setForeground(Color.WHITE);
            }
            public void mouseExited(MouseEvent e) {
                registerBtn.setBackground(CARD_COLOR);
                registerBtn.setForeground(BLUE);
            }
        });
        cardPanel.add(registerBtn);

        // ── Role badge ───────────────────────────────────────────────────
        JLabel roleBadge = new JLabel("", SwingConstants.CENTER);
        roleBadge.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        roleBadge.setForeground(TEXT_MUTED);
        roleBadge.setBounds(60, 400, 300, 20);
        roleBadge.setText("Customer credentials required");
        cardPanel.add(roleBadge);

        // ── Actions ───────────────────────────────────────────────────────
        loginBtn.addActionListener(e -> performLogin());
        passwordField.addActionListener(e -> performLogin()); // Enter key
        registerBtn.addActionListener(e -> new RegisterFrame(this).setVisible(true));

        customerTabBtn.addActionListener(e -> {
            if (isAdminMode) switchMode(false, header, subHeader, loginBtn, registerBtn, roleBadge);
        });
        adminTabBtn.addActionListener(e -> {
            if (!isAdminMode) switchMode(true, header, subHeader, loginBtn, registerBtn, roleBadge);
        });

        setContentPane(mainPanel);
    }

    // ── Tab helper ────────────────────────────────────────────────────────
    private JButton makeTabButton(String text, boolean active) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        applyTabStyle(btn, active);
        return btn;
    }

    private void applyTabStyle(JButton btn, boolean active) {
        if (active) {
            btn.setBackground(BLUE);
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(new Color(233, 236, 239));
            btn.setForeground(TEXT_MUTED);
        }
    }

    // ── Switch between Customer / Admin mode ─────────────────────────────
    private void switchMode(boolean adminMode, JLabel header, JLabel subHeader,
                            JButton loginBtn, JButton registerBtn, JLabel roleBadge) {
        isAdminMode = adminMode;
        usernameField.setText("");
        passwordField.setText("");

        if (adminMode) {
            applyTabStyle(adminTabBtn, true);
            applyTabStyle(customerTabBtn, false);
            header.setText("Admin Access");
            subHeader.setText("Sign in to your dashboard");
            loginBtn.setText("Login as Admin");
            loginBtn.setBackground(RED);
            registerBtn.setVisible(false);
            roleBadge.setText("Admin credentials required");
        } else {
            applyTabStyle(customerTabBtn, true);
            applyTabStyle(adminTabBtn, false);
            header.setText("Customer Login");
            subHeader.setText("Sign in to your account");
            loginBtn.setText("Login");
            loginBtn.setBackground(BLUE);
            registerBtn.setVisible(true);
            roleBadge.setText("Customer credentials required");
        }
    }

    // ── Login logic ───────────────────────────────────────────────────────
    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = userDAO.authenticate(username, password);
        if (user != null) {
            if (isAdminMode) {
                if ("admin".equals(user.getRole())) {
                    dispose();
                    new AdminFrame(user).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "This is a customer account. Please use the Customer tab.", "Access Denied", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                if (!"admin".equals(user.getRole())) {
                    dispose();
                    new CustomerFrame(user).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Admin accounts must use the Admin Login tab.", "Access Denied", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}