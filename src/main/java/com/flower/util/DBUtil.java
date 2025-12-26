package com.flower.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:sqlserver://localhost:1433;DatabaseName=flower_shop;encrypt=false";
    private static final String USER = "sa";
    private static final String PWD = "123456"; // 只改这里！换成你的数据库密码

    static {
        try {Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");}
        catch (ClassNotFoundException e) {e.printStackTrace();}
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {conn = DriverManager.getConnection(URL, USER, PWD);}
        catch (SQLException e) {e.printStackTrace();}
        return conn;
    }

    public static void close(Connection conn) {
        if (conn != null) {try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
    }
    public static void close(Connection conn, PreparedStatement pstmt) {
        close(conn);if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}}
    }
    public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        close(conn, pstmt);if (rs != null) {try {rs.close();} catch (SQLException e) {e.printStackTrace();}}
    }
}