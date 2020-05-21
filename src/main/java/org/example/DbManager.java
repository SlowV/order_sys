package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbManager {
    private static Connection conn;
    private static String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&serverTimezone=Asia/Ho_Chi_Minh&characterEncoding=utf-8";
    private static String user = "root";
    private static String password = "";

    public static Connection getConnection() {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
