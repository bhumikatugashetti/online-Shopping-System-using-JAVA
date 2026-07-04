package gui;

import dao.ProductDAO;
import dao.UserDAO;
import dao.OrderDAO;
import model.Product;
import model.User;
import model.Order;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminFrame extends JFrame {
    private User admin;
    private ProductDAO productDAO;
    private UserDAO userDAO;
    private OrderDAO orderDAO;

    public AdminFrame(User admin) {
        this.admin = admin;
        productDAO = new ProductDAO();
        userDAO = new UserDAO();
        orderDAO = new OrderDAO();

        setTitle("Admin Dashboard - " + admin.getFullName());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Products", createProductsPanel());
        tabs.addTab("Users", createUsersPanel());
        tabs.addTab("Orders", createOrdersPanel());

        add(tabs, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
        bottomPanel.add(logoutBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createProductsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columns = {"ID", "Name", "Description", "Price", "Stock", "Category"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Add Product");
        JButton refreshBtn = new JButton("Refresh");

        btnPanel.add(addBtn);
        btnPanel.add(refreshBtn);

        panel.add(btnPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadProducts(model);

        refreshBtn.addActionListener(e -> loadProducts(model));

        addBtn.addActionListener(e -> {
            JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
            JTextField nameField = new JTextField();
            JTextField descField = new JTextField();
            JTextField priceField = new JTextField();
            JTextField stockField = new JTextField();
            JComboBox<String> categoryCombo = new JComboBox<>(new String[]{"Electronics", "Clothing", "Footwear", "Home"});

            inputPanel.add(new JLabel("Name:"));
            inputPanel.add(nameField);
            inputPanel.add(new JLabel("Description:"));
            inputPanel.add(descField);
            inputPanel.add(new JLabel("Price:"));
            inputPanel.add(priceField);
            inputPanel.add(new JLabel("Stock:"));
            inputPanel.add(stockField);
            inputPanel.add(new JLabel("Category:"));
            inputPanel.add(categoryCombo);

            int result = JOptionPane.showConfirmDialog(this, inputPanel, "Add Product", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    Product p = new Product(nameField.getText(), descField.getText(),
                            Double.parseDouble(priceField.getText()),
                            Integer.parseInt(stockField.getText()),
                            (String) categoryCombo.getSelectedItem());
                    if (productDAO.addProduct(p)) {
                        loadProducts(model);
                        JOptionPane.showMessageDialog(this, "Product added successfully!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid price or stock value", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return panel;
    }

    private void loadProducts(DefaultTableModel model) {
        model.setRowCount(0);
        List<Product> products = productDAO.getAllProducts();
        for (Product p : products) {
            model.addRow(new Object[]{p.getProductId(), p.getProductName(), p.getDescription(),
                    "$" + p.getPrice(), p.getStockQuantity(), p.getCategory()});
        }
    }

    private JPanel createUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columns = {"ID", "Username", "Full Name", "Email", "Phone", "Role"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JButton refreshBtn = new JButton("Refresh");

        JPanel btnPanel = new JPanel();
        btnPanel.add(refreshBtn);
        panel.add(btnPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        List<User> users = userDAO.getAllUsers();
        for (User u : users) {
            model.addRow(new Object[]{u.getUserId(), u.getUsername(), u.getFullName(),
                    u.getEmail(), u.getPhone(), u.getRole()});
        }

        refreshBtn.addActionListener(e -> {
            model.setRowCount(0);
            List<User> userList = userDAO.getAllUsers();
            for (User u : userList) {
                model.addRow(new Object[]{u.getUserId(), u.getUsername(), u.getFullName(),
                        u.getEmail(), u.getPhone(), u.getRole()});
            }
        });

        return panel;
    }

    private JPanel createOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columns = {"Order ID", "User ID", "Date", "Total", "Status", "Shipping Address"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton refreshBtn = new JButton("Refresh");
        JButton deliverBtn = new JButton("Mark as Delivered");
        JPanel btnPanel = new JPanel();
        btnPanel.add(refreshBtn);
        btnPanel.add(deliverBtn);

        panel.add(btnPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadAllOrders(model);

        refreshBtn.addActionListener(e -> loadAllOrders(model));

        deliverBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an order to update", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int orderId = (int) model.getValueAt(selectedRow, 0);
            if (orderDAO.updateOrderStatus(orderId, "Delivered")) {
                loadAllOrders(model);
                JOptionPane.showMessageDialog(this, "Order marked as delivered!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update order status.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private void loadAllOrders(DefaultTableModel model) {
        model.setRowCount(0);
        List<Order> orders = orderDAO.getAllOrders();
        for (Order o : orders) {
            model.addRow(new Object[]{o.getOrderId(), o.getUserId(), o.getOrderDate(),
                    "$" + o.getTotalAmount(), o.getStatus(), o.getShippingAddress()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            User adminUser = new User("admin", "password123", "Admin", "admin@shop.com");
            new AdminFrame(adminUser).setVisible(true);
        });
    }
}