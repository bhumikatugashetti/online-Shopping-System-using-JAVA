package gui;

import model.User;
import dao.UserDAO;
import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private JTextField usernameField, fullNameField, emailField, phoneField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton registerBtn, cancelBtn;
    private JFrame parentFrame;

    public RegisterFrame(JFrame parent) {
        this.parentFrame = parent;
        setTitle("Register New User");
        setSize(400, 350);
        setLocationRelativeTo(parent);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Username:", "Password:", "Confirm Password:", "Full Name:", "Email:", "Phone:"};
        JTextField[] fields = new JTextField[6];

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            mainPanel.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1;
            if (i == 1 || i == 2) {
                fields[i] = new JPasswordField(20);
            } else {
                fields[i] = new JTextField(20);
            }
            mainPanel.add(fields[i], gbc);
        }

        usernameField = fields[0];
        passwordField = (JPasswordField) fields[1];
        confirmPasswordField = (JPasswordField) fields[2];
        fullNameField = fields[3];
        emailField = fields[4];
        phoneField = fields[5];

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new FlowLayout());
        registerBtn = new JButton("Register");
        cancelBtn = new JButton("Cancel");
        btnPanel.add(registerBtn);
        btnPanel.add(cancelBtn);
        mainPanel.add(btnPanel, gbc);

        registerBtn.addActionListener(e -> performRegister());
        cancelBtn.addActionListener(e -> dispose());

        add(mainPanel);
    }

    private void performRegister() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = new User(username, password, fullName, email);
        user.setPhone(phone);

        UserDAO userDAO = new UserDAO();
        if (userDAO.register(user)) {
            JOptionPane.showMessageDialog(this, "Registration successful! Please login.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Username or email may already exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}