package com.flower.dao;
import com.flower.pojo.Flower;
import com.flower.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlowerDAO {
    // ========== 修复findAll()：新增conn非空判断+finally关闭资源 ==========
    public List<Flower> findAll() {
        List<Flower> list = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        // 核心修复1：判断conn是否为null，避免空指针
        if (conn == null) {
            System.err.println("FlowerDAO.findAll - 数据库连接失败！");
            return list;
        }
        String sql = "SELECT f.*,i.stock FROM flower f LEFT JOIN inventory i ON f.flower_id=i.flower_id";
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()) list.add(getFlower(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 核心修复2：移到finally，确保资源必关闭
            DBUtil.close(conn,pstmt,rs);
        }
        return list;
    }

    // ========== 修复findByCategory()：新增conn非空判断+finally关闭资源 ==========
    public List<Flower> findByCategory(String category) {
        List<Flower> list = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (conn == null) {
            System.err.println("FlowerDAO.findByCategory - 数据库连接失败！");
            return list;
        }
        String sql = "SELECT f.*,i.stock FROM flower f LEFT JOIN inventory i ON f.flower_id=i.flower_id WHERE f.category=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,category);
            rs = pstmt.executeQuery();
            while(rs.next()) list.add(getFlower(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn,pstmt,rs);
        }
        return list;
    }

    // ========== 修复findByPage()：新增conn非空判断+finally关闭资源 ==========
    public List<Flower> findByPage(int start, int pageSize) {
        List<Flower> list = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (conn == null) {
            System.err.println("FlowerDAO.findByPage - 数据库连接失败！");
            return list;
        }
        String sql = "SELECT f.*,i.stock FROM flower f LEFT JOIN inventory i ON f.flower_id=i.flower_id ORDER BY f.flower_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,start);pstmt.setInt(2,pageSize);
            rs = pstmt.executeQuery();
            while(rs.next()) list.add(getFlower(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn,pstmt,rs);
        }
        return list;
    }

    // ========== 修复getTotalCount()：新增conn非空判断+finally关闭资源 ==========
    public int getTotalCount() {
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (conn == null) {
            System.err.println("FlowerDAO.getTotalCount - 数据库连接失败！");
            return 0;
        }
        String sql = "SELECT COUNT(*) FROM flower";
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if(rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn,pstmt,rs); // 移到finally，确保必执行
        }
        return 0;
    }

    // ========== 修复add()：新增conn非空判断+统一finally关闭资源 ==========
    public boolean add(Flower flower, int stock) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt1 = null;
        ResultSet rs = null;
        PreparedStatement pstmt3 = null;
        if (conn == null) {
            System.err.println("FlowerDAO.add - 数据库连接失败！");
            return false;
        }
        try {
            conn.setAutoCommit(false);
            String sql1 = "INSERT INTO flower(flower_name,price,category,description,img_url) VALUES(?,?,?,?,?)";
            pstmt1 = conn.prepareStatement(sql1);
            setFlowerParam(pstmt1,flower);
            pstmt1.executeUpdate();

            String sql2 = "SELECT SCOPE_IDENTITY()";
            rs = conn.prepareStatement(sql2).executeQuery();
            int fid = 0;if(rs.next()) fid = rs.getInt(1);

            String sql3 = "INSERT INTO inventory(flower_id,stock) VALUES(?,?)";
            pstmt3 = conn.prepareStatement(sql3);
            pstmt3.setInt(1,fid);pstmt3.setInt(2,stock);
            pstmt3.executeUpdate();
            conn.commit();
            return true;
        } catch (SQLException e) {
            try {if(conn != null) conn.rollback();} catch (SQLException ex) {ex.printStackTrace();}
            e.printStackTrace();
            return false;
        } finally {
            // 统一关闭所有资源
            DBUtil.close(conn,pstmt1,rs);
            DBUtil.close(null,pstmt3,null);
        }
    }

    // ========== 修复update()：新增conn非空判断+finally关闭资源 ==========
    public boolean update(Flower flower) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        if (conn == null) {
            System.err.println("FlowerDAO.update - 数据库连接失败！");
            return false;
        }
        String sql = "UPDATE flower SET flower_name=?,price=?,category=?,description=?,img_url=? WHERE flower_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            setFlowerParam(pstmt,flower);pstmt.setInt(6,flower.getFlowerId());
            int rows = pstmt.executeUpdate();
            return rows>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn,pstmt,null);
        }
    }

    // ========== 修复delete()：新增conn非空判断+finally关闭资源 ==========
    public boolean delete(int fid) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        if (conn == null) {
            System.err.println("FlowerDAO.delete - 数据库连接失败！");
            return false;
        }
        String sql = "DELETE FROM flower WHERE flower_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,fid);
            int rows = pstmt.executeUpdate();
            return rows>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn,pstmt,null);
        }
    }

    // ========== 修复reduceStock()：新增conn非空判断+finally关闭资源 ==========
    public boolean reduceStock(int fid, int num) {
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        if (conn == null) {
            System.err.println("FlowerDAO.reduceStock - 数据库连接失败！");
            return false;
        }
        String sql = "UPDATE inventory SET stock=stock-? WHERE flower_id=? AND stock>=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,num);pstmt.setInt(2,fid);pstmt.setInt(3,num);
            int rows = pstmt.executeUpdate();
            return rows>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn,pstmt,null);
        }
    }

    // 以下两个私有方法无问题，无需修改
    private Flower getFlower(ResultSet rs) throws SQLException {
        Flower flower = new Flower();
        flower.setFlowerId(rs.getInt("flower_id"));
        flower.setFlowerName(rs.getString("flower_name"));
        flower.setPrice(rs.getDouble("price"));
        flower.setCategory(rs.getString("category"));
        flower.setDescription(rs.getString("description"));
        flower.setImgUrl(rs.getString("img_url"));
        flower.setStock(rs.getInt("stock"));
        return flower;
    }

    private void setFlowerParam(PreparedStatement pstmt, Flower flower) throws SQLException {
        pstmt.setString(1,flower.getFlowerName());
        pstmt.setDouble(2,flower.getPrice());
        pstmt.setString(3,flower.getCategory());
        pstmt.setString(4,flower.getDescription());
        pstmt.setString(5,flower.getImgUrl());
    }
}