package db;

import java.sql.*;

public class CheckDatabase {
    public static void main(String[] args) {
        System.out.println("Checking XAMPP MySQL Database...");
        System.out.println();

        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306", "root", "");

            System.out.println("Connected to MySQL!");
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW DATABASES");
            
            System.out.println("Available Databases:");
            while (rs.next()) {
                System.out.println("  - " + rs.getString(1));
            }
            
            System.out.println();
            rs = stmt.executeQuery("SHOW DATABASES LIKE 'online_shopping'");
            if (rs.next()) {
                System.out.println("online_shopping database EXISTS!");
                
                stmt.execute("USE online_shopping");
                ResultSet tables = stmt.executeQuery("SHOW TABLES");
                System.out.println("Tables in online_shopping:");
                while (tables.next()) {
                    System.out.println("  - " + tables.getString(1));
                }
                
                ResultSet users = stmt.executeQuery("SELECT * FROM users");
                System.out.println("\nUsers in database:");
                while (users.next()) {
                    System.out.println("  - " + users.getString("username") + " (" + users.getString("role") + ")");
                }
            } else {
                System.out.println("online_shopping database NOT FOUND!");
            }
            
            conn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}