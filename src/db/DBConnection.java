package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBConnection {
    private static String URL = "jdbc:mysql://localhost:3306/online_shopping";
    private static String USERNAME = "root";
    private static String PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found!");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void loadProperties(String filePath) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            props.load(fis);
            URL = props.getProperty("db.url", URL);
            USERNAME = props.getProperty("db.username", USERNAME);
            PASSWORD = props.getProperty("db.password", PASSWORD);
        } catch (IOException e) {
            System.out.println("Could not load database properties file.");
        }
    }
}