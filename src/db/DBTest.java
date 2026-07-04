package db;

import java.sql.*;

public class DBTest {
    public static void main(String[] args) {
        System.out.println("Testing MySQL Connection via XAMPP...");
        System.out.println("URL: jdbc:mysql://localhost:3306/online_shopping");
        System.out.println("User: root");
        System.out.println("Password: (empty)");
        System.out.println();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/online_shopping", "root", "");
            System.out.println("SUCCESS: Connected to MySQL via XAMPP!");
            System.out.println("Connection: " + conn);

            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("MySQL Version: " + meta.getDatabaseProductVersion());

            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: MySQL JDBC Driver not found!");
            System.out.println("Make sure mysql-connector-j-8.0.33.jar is in the lib folder.");
        } catch (SQLException e) {
            System.out.println("ERROR: Could not connect to MySQL!");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println();
            System.out.println("Troubleshooting:");
            System.out.println("1. Make sure XAMPP MySQL service is RUNNING");
            System.out.println("2. Create the database by running database.sql in phpMyAdmin");
            System.out.println("3. Check that port 3306 is not blocked");
        }
    }
}