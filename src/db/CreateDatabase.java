package db;

import java.sql.*;

public class CreateDatabase {
    public static void main(String[] args) {
        System.out.println("Creating database in XAMPP MySQL...");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306", "root", "");

            Statement stmt = conn.createStatement();

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS online_shopping");
            System.out.println("Database 'online_shopping' created!");

            stmt.executeUpdate("USE online_shopping");
            System.out.println("Using database...");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                "user_id INT PRIMARY KEY AUTO_INCREMENT," +
                "username VARCHAR(50) UNIQUE NOT NULL," +
                "password VARCHAR(100) NOT NULL," +
                "full_name VARCHAR(100) NOT NULL," +
                "email VARCHAR(100) UNIQUE NOT NULL," +
                "phone VARCHAR(20)," +
                "address TEXT," +
                "role VARCHAR(20) DEFAULT 'customer'," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS products (" +
                "product_id INT PRIMARY KEY AUTO_INCREMENT," +
                "product_name VARCHAR(100) NOT NULL," +
                "description TEXT," +
                "price DECIMAL(10, 2) NOT NULL," +
                "stock_quantity INT DEFAULT 0," +
                "category VARCHAR(50)," +
                "image_url VARCHAR(200)," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS orders (" +
                "order_id INT PRIMARY KEY AUTO_INCREMENT," +
                "user_id INT NOT NULL," +
                "order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "total_amount DECIMAL(10, 2) NOT NULL," +
                "status VARCHAR(20) DEFAULT 'pending'," +
                "shipping_address TEXT," +
                "FOREIGN KEY (user_id) REFERENCES users(user_id))");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS order_items (" +
                "order_item_id INT PRIMARY KEY AUTO_INCREMENT," +
                "order_id INT NOT NULL," +
                "product_id INT NOT NULL," +
                "quantity INT NOT NULL," +
                "price DECIMAL(10, 2) NOT NULL," +
                "FOREIGN KEY (order_id) REFERENCES orders(order_id)," +
                "FOREIGN KEY (product_id) REFERENCES products(product_id))");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS cart (" +
                "cart_id INT PRIMARY KEY AUTO_INCREMENT," +
                "user_id INT NOT NULL," +
                "product_id INT NOT NULL," +
                "quantity INT DEFAULT 1," +
                "added_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (user_id) REFERENCES users(user_id)," +
                "FOREIGN KEY (product_id) REFERENCES products(product_id))");

            System.out.println("All tables created!");

            stmt.executeUpdate("INSERT INTO users (username, password, full_name, email, phone, role) VALUES " +
                "('admin', 'password123', 'Admin User', 'admin@shop.com', '1234567890', 'admin')," +
                "('john', 'password123', 'John Doe', 'john@email.com', '9876543210', 'customer')," +
                "('jane', 'password123', 'Jane Smith', 'jane@email.com', '5551234567', 'customer') " +
                "ON DUPLICATE KEY UPDATE username=username");

            stmt.executeUpdate("INSERT INTO products (product_name, description, price, stock_quantity, category) VALUES " +
                "('Laptop', 'High-performance laptop with 16GB RAM', 999.99, 50, 'Electronics')," +
                "('Smartphone', 'Latest smartphone with 5G connectivity', 699.99, 100, 'Electronics')," +
                "('Headphones', 'Wireless noise-canceling headphones', 199.99, 75, 'Electronics')," +
                "('T-Shirt', 'Cotton casual t-shirt', 29.99, 200, 'Clothing')," +
                "('Jeans', 'Classic fit denim jeans', 49.99, 150, 'Clothing')," +
                "('Sneakers', 'Comfortable running sneakers', 89.99, 80, 'Footwear')," +
                "('Coffee Maker', 'Automatic coffee maker with timer', 79.99, 40, 'Home')," +
                "('Desk Lamp', 'LED desk lamp with adjustable brightness', 39.99, 60, 'Home') " +
                "ON DUPLICATE KEY UPDATE product_name=product_name");

            System.out.println("Sample data inserted!");
            System.out.println("Database setup complete!");

            conn.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}