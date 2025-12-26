package com.flower.dao;
import com.flower.pojo.User;
import com.flower.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public User login(String username, String password) {
        Connection conn = DBUtil.getConnection();
        String sql = "SELECT * FROM [user] WHERE username=? AND password=?";
        User user = null;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);pstmt.setString(2,password);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setRole(rs.getString("role"));
            }
            DBUtil.close(conn,pstmt,rs);
        } catch (SQLException e) {e.printStackTrace();}
        return user;
    }

    public boolean register(User user) {
        Connection conn = DBUtil.getConnection();
        String sql = "INSERT INTO [user](username,password,phone,address) VALUES(?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,user.getUsername());pstmt.setString(2,user.getPassword());
            pstmt.setString(3,user.getPhone());pstmt.setString(4,user.getAddress());
            int rows = pstmt.executeUpdate();
            DBUtil.close(conn,pstmt);
            return rows>0;
        } catch (SQLException e) {e.printStackTrace();return false;}
    }

    public boolean checkUsername(String username) {
        Connection conn = DBUtil.getConnection();
        String sql = "SELECT * FROM [user] WHERE username=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            ResultSet rs = pstmt.executeQuery();
            boolean exist = rs.next();
            DBUtil.close(conn,pstmt,rs);
            return exist;
        } catch (SQLException e) {e.printStackTrace();return false;}
    }

    public boolean updateUser(User user) {
        Connection conn = DBUtil.getConnection();
        String sql = "UPDATE [user] SET phone=?,address=? WHERE user_id=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,user.getPhone());pstmt.setString(2,user.getAddress());pstmt.setInt(3,user.getUserId());
            int rows = pstmt.executeUpdate();
            DBUtil.close(conn,pstmt);
            return rows>0;
        } catch (SQLException e) {e.printStackTrace();return false;}
    }
}