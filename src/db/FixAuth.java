package db;

import java.sql.*;

public class FixAuth {
    public static void main(String[] args) {
        System.out.println("Fixing MariaDB (XAMPP) authentication...");
        
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306", "root", "");

            Statement stmt = conn.createStatement();
            
            stmt.executeUpdate("SET PASSWORD FOR 'root'@'localhost' = PASSWORD('')");
            System.out.println("Password reset to empty!");
            
            System.out.println("Now refresh phpMyAdmin - it should work!");
            
            conn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}