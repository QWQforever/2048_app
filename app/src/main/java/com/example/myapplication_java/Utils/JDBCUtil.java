package com.example.myapplication_java.Utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class JDBCUtil {
    //第一步
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    //第二步
    public static Connection getConn() {
        Connection conn = null;
        try {
            System.out.println("qqqqq");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/Score?useSSL=false", "root", "12345678");//注意格式和空格
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return conn;
    }

    public static void close(Connection conn) {
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
