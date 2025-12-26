package com.flower.dao;
import com.flower.pojo.Announcement;
import com.flower.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementDAO {
    public List<Announcement> findAll() {
        List<Announcement> list = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        // 新增：判断连接是否获取成功
        if (conn == null) {
            System.err.println("数据库连接获取失败");
            return list; // 或抛出自定义异常
        }
        String sql = "SELECT * FROM announcement ORDER BY publish_time DESC";
        PreparedStatement pstmt = null;
        ResultSet rs = null; // 提前声明，方便统一关闭
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Announcement ann = new Announcement();
                ann.setAnnId(rs.getInt("ann_id"));
                ann.setTitle(rs.getString("title"));
                ann.setContent(rs.getString("content"));
                ann.setPublishTime(rs.getDate("publish_time"));
                list.add(ann);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 建议记录详细日志，方便排查
        } finally {
            // 移到finally块，确保无论是否异常都会关闭资源
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }

    // 在AnnouncementDAO类中补充delete方法（参数必须是int类型）
    public boolean delete(int annId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection(); // 确保DBUtil的方法名正确
            if (conn == null) {
                return false;
            }
            String sql = "DELETE FROM announcement WHERE ann_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, annId); // 匹配int类型参数
            int rows = pstmt.executeUpdate();
            return rows > 0; // 影响行数>0则删除成功
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null); // 关闭资源
        }
    }

    public boolean add(Announcement ann) {
        Connection conn = DBUtil.getConnection();
        if (conn == null) {
            System.err.println("数据库连接获取失败");
            return false;
        }
        String sql = "INSERT INTO announcement(title,content) VALUES(?,?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ann.getTitle());
            pstmt.setString(2, ann.getContent());
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // 建议补充日志："新增公告失败，标题：" + ann.getTitle()
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null); // 关闭连接和PreparedStatement
        }
    }
}