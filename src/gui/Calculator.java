package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private String currentInput = "";
    private double result = 0;
    private String operator = "";
    private boolean startNewInput = true;

    public Calculator() {
        setTitle("Simple Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setPreferredSize(new Dimension(300, 60));
        panel.add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));

        String[] buttons = {
            "C", "←", "%", "/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "=", ""
        };

        for (String text : buttons) {
            if (text.isEmpty()) {
                JButton empty = new JButton();
                empty.setEnabled(false);
                buttonPanel.add(empty);
            } else {
                JButton btn = new JButton(text);
                btn.setFont(new Font("Arial", Font.PLAIN, 20));
                btn.setFocusPainted(false);
                btn.addActionListener(this);
                buttonPanel.add(btn);
            }
        }

        panel.add(buttonPanel, BorderLayout.CENTER);
        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.matches("[0-9]")) {
            if (startNewInput) {
                currentInput = command;
                startNewInput = false;
            } else {
                currentInput += command;
            }
            display.setText(currentInput);
        }
        else if (command.equals(".")) {
            if (startNewInput) {
                currentInput = "0.";
                startNewInput = false;
            } else if (!currentInput.contains(".")) {
                currentInput += ".";
            }
            display.setText(currentInput);
        }
        else if (command.equals("C")) {
            currentInput = "";
            result = 0;
            operator = "";
            startNewInput = true;
            display.setText("0");
        }
        else if (command.equals("←")) {
            if (currentInput.length() > 1) {
                currentInput = currentInput.substring(0, currentInput.length() - 1);
            } else {
                currentInput = "";
                startNewInput = true;
            }
            display.setText(currentInput.isEmpty() ? "0" : currentInput);
        }
        else if (command.equals("+") || command.equals("-") || command.equals("*") || command.equals("/") || command.equals("%")) {
            if (!currentInput.isEmpty()) {
                result = Double.parseDouble(currentInput);
            }
            operator = command;
            startNewInput = true;
        }
        else if (command.equals("=")) {
            if (!operator.isEmpty() && !currentInput.isEmpty()) {
                double operand = Double.parseDouble(currentInput);
                switch (operator) {
                    case "+": result += operand; break;
                    case "-": result -= operand; break;
                    case "*": result *= operand; break;
                    case "/": result /= operand; break;
                    case "%": result %= operand; break;
                }
                display.setText(String.valueOf(result));
                currentInput = String.valueOf(result);
                operator = "";
                startNewInput = true;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calc = new Calculator();
            calc.setVisible(true);
        });
    }
}
