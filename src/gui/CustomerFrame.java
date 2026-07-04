package gui;

import dao.ProductDAO;
import dao.CartDAO;
import dao.OrderDAO;
import model.Product;
import model.CartItem;
import model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerFrame extends JFrame {
    private User user;
    private ProductDAO productDAO;
    private CartDAO cartDAO;
    private OrderDAO orderDAO;
    private JTable productTable, cartTable;
    private DefaultTableModel productModel, cartModel, orderModel;
    private JTextField searchField;
    private JComboBox<String> categoryCombo;
    private JLabel totalLabel;

    public CustomerFrame(User user) {
        this.user = user;
        productDAO = new ProductDAO();
        cartDAO = new CartDAO();
        orderDAO = new OrderDAO();

        setTitle("Customer - " + user.getFullName());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Products", createProductsPanel());
        tabs.addTab("Shopping Cart", createCartPanel());
        tabs.addTab("My Orders", createOrdersPanel());
        tabs.addTab("Profile", createProfilePanel());

        add(tabs);
    }

    private JPanel createProductsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        searchField.setToolTipText("Search products...");
        JButton searchBtn = new JButton("Search");
        categoryCombo = new JComboBox<>(new String[]{"All", "Electronics", "Clothing", "Footwear", "Home"});
        JButton addToCartBtn = new JButton("Add to Cart");

        topPanel.add(new JLabel("Search:"));
        topPanel.add(searchField);
        topPanel.add(searchBtn);
        topPanel.add(new JLabel("Category:"));
        topPanel.add(categoryCombo);
        topPanel.add(addToCartBtn);

        String[] columns = {"ID", "Name", "Description", "Price", "Stock", "Category"};
        productModel = new DefaultTableModel(columns, 0);
        productTable = new JTable(productModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(productTable);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadProducts();

        searchBtn.addActionListener(e -> searchProducts());
        categoryCombo.addActionListener(e -> filterByCategory());
        addToCartBtn.addActionListener(e -> addToCart());

        return panel;
    }

    private void loadProducts() {
        productModel.setRowCount(0);
        List<Product> products = productDAO.getAllProducts();
        for (Product p : products) {
            productModel.addRow(new Object[]{p.getProductId(), p.getProductName(), p.getDescription(),
                    "$" + p.getPrice(), p.getStockQuantity(), p.getCategory()});
        }
    }

    private void searchProducts() {
        String keyword = searchField.getText().trim();
        productModel.setRowCount(0);
        List<Product> products;
        if (keyword.isEmpty()) {
            products = productDAO.getAllProducts();
        } else {
            products = productDAO.searchProducts(keyword);
        }
        for (Product p : products) {
            productModel.addRow(new Object[]{p.getProductId(), p.getProductName(), p.getDescription(),
                    "$" + p.getPrice(), p.getStockQuantity(), p.getCategory()});
        }
    }

    private void filterByCategory() {
        String category = (String) categoryCombo.getSelectedItem();
        productModel.setRowCount(0);
        List<Product> products;
        if ("All".equals(category)) {
            products = productDAO.getAllProducts();
        } else {
            products = productDAO.getProductsByCategory(category);
        }
        for (Product p : products) {
            productModel.addRow(new Object[]{p.getProductId(), p.getProductName(), p.getDescription(),
                    "$" + p.getPrice(), p.getStockQuantity(), p.getCategory()});
        }
    }

    private void addToCart() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int productId = (int) productModel.getValueAt(selectedRow, 0);
        String quantityStr = JOptionPane.showInputDialog(this, "Enter quantity:", "1");
        if (quantityStr == null) return;

        try {
            int quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) throw new NumberFormatException();

            if (cartDAO.addToCart(user.getUserId(), productId, quantity)) {
                JOptionPane.showMessageDialog(this, "Product added to cart!", "Success", JOptionPane.INFORMATION_MESSAGE);
                if (totalLabel != null) {
                    loadCart(totalLabel);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid quantity", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createCartPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columns = {"ID", "Product", "Price", "Quantity", "Subtotal"};
        cartModel = new DefaultTableModel(columns, 0);
        cartTable = new JTable(cartModel);
        JScrollPane scrollPane = new JScrollPane(cartTable);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton removeBtn = new JButton("Remove");
        JButton clearBtn = new JButton("Clear Cart");
        JButton checkoutBtn = new JButton("Checkout");
        totalLabel = new JLabel();

        bottomPanel.add(removeBtn);
        bottomPanel.add(clearBtn);
        bottomPanel.add(totalLabel);
        bottomPanel.add(checkoutBtn);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        loadCart(totalLabel);

        removeBtn.addActionListener(e -> {
            int row = cartTable.getSelectedRow();
            if (row >= 0) {
                int cartId = (int) cartModel.getValueAt(row, 0);
                cartDAO.removeFromCart(cartId);
                loadCart(totalLabel);
            }
        });

        clearBtn.addActionListener(e -> {
            cartDAO.clearCart(user.getUserId());
            loadCart(totalLabel);
        });

        checkoutBtn.addActionListener(e -> {
            if (cartDAO.getCartItems(user.getUserId()).isEmpty()) {
                JOptionPane.showMessageDialog(this, "Cart is empty", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String address = JOptionPane.showInputDialog(this, "Enter shipping address:", "Enter address");
            if (address != null && !address.trim().isEmpty()) {
                int orderId = orderDAO.createOrder(user.getUserId(), address);
                if (orderId > 0) {
                    JOptionPane.showMessageDialog(this, "Order placed successfully! Order ID: " + orderId, "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadCart(totalLabel);
                    if (orderModel != null) {
                        loadOrders();
                    }
                }
            }
        });

        return panel;
    }

    private void loadCart(JLabel totalLabel) {
        cartModel.setRowCount(0);
        List<CartItem> items = cartDAO.getCartItems(user.getUserId());
        for (CartItem item : items) {
            cartModel.addRow(new Object[]{item.getCartId(), item.getProduct().getProductName(),
                    "$" + item.getProduct().getPrice(), item.getQuantity(), "$" + item.getSubtotal()});
        }
        totalLabel.setText("Total: $" + cartDAO.getCartTotal(user.getUserId()));
    }

    private JPanel createOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"Order ID", "Date", "Total", "Status", "Shipping Address"};
        orderModel = new DefaultTableModel(columns, 0);
        JTable orderTable = new JTable(orderModel);
        JScrollPane scrollPane = new JScrollPane(orderTable);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshBtn = new JButton("Refresh");
        btnPanel.add(refreshBtn);

        loadOrders();

        refreshBtn.addActionListener(e -> loadOrders());

        panel.add(btnPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void loadOrders() {
        if (orderModel == null) return;
        orderModel.setRowCount(0);
        List<model.Order> orders = orderDAO.getUserOrders(user.getUserId());
        for (model.Order o : orders) {
            orderModel.addRow(new Object[]{o.getOrderId(), o.getOrderDate(), "$" + o.getTotalAmount(),
                    o.getStatus(), o.getShippingAddress()});
        }
    }

    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        panel.add(new JLabel(user.getUsername()));
        panel.add(new JLabel("Full Name:"));
        panel.add(new JLabel(user.getFullName()));
        panel.add(new JLabel("Email:"));
        panel.add(new JLabel(user.getEmail()));
        panel.add(new JLabel("Phone:"));
        panel.add(new JLabel(user.getPhone() != null ? user.getPhone() : "N/A"));
        panel.add(new JLabel("Address:"));
        panel.add(new JLabel(user.getAddress() != null ? user.getAddress() : "N/A"));
        panel.add(new JLabel("Role:"));
        panel.add(new JLabel(user.getRole()));
        panel.add(new JLabel("Member Since:"));
        panel.add(new JLabel(user.getCreatedAt() != null ? user.getCreatedAt() : "N/A"));

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
        panel.add(new JLabel());
        panel.add(logoutBtn);

        return panel;
    }
}