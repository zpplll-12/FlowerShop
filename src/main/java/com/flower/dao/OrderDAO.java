package com.flower.dao;
import com.flower.pojo.Order;
import com.flower.pojo.OrderItem;
import com.flower.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public boolean addOrder(Order order) {
        Connection conn = DBUtil.getConnection();
        try {
            conn.setAutoCommit(false);
            String sql1 = "INSERT INTO [order](user_id,total_price,order_status,receiver,phone,address) VALUES(?,?,?,?,?,?)";
            PreparedStatement pstmt1 = conn.prepareStatement(sql1,PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt1.setInt(1,order.getUserId());pstmt1.setDouble(2,order.getTotalPrice());
            pstmt1.setString(3,order.getOrderStatus());pstmt1.setString(4,order.getReceiver());
            pstmt1.setString(5,order.getPhone());pstmt1.setString(6,order.getAddress());
            pstmt1.executeUpdate();

            ResultSet rs = pstmt1.getGeneratedKeys();
            int oid =0;if(rs.next()) oid=rs.getInt(1);

            String sql2 = "INSERT INTO order_item(order_id,flower_id,num,price) VALUES(?,?,?,?)";
            PreparedStatement pstmt2 = conn.prepareStatement(sql2);
            for(OrderItem item : order.getItemList()){
                pstmt2.setInt(1,oid);pstmt2.setInt(2,item.getFlowerId());
                pstmt2.setInt(3,item.getNum());pstmt2.setDouble(4,item.getPrice());
                pstmt2.addBatch();
            }
            pstmt2.executeBatch();conn.commit();
            DBUtil.close(conn,pstmt1,rs);DBUtil.close(null,pstmt2);
            return true;
        } catch (SQLException e) {
            try {conn.rollback();} catch (SQLException ex) {ex.printStackTrace();}
            e.printStackTrace();return false;
        }
    }

    public List<Order> findByUserId(int uid) {
        List<Order> list = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        String sql = "SELECT * FROM [order] WHERE user_id=? ORDER BY order_time DESC";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,uid);ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Order order = getOrder(rs);
                order.setItemList(findOrderItem(order.getOrderId()));
                list.add(order);
            }
            DBUtil.close(conn,pstmt,rs);
        } catch (SQLException e) {e.printStackTrace();}
        return list;
    }

    public boolean updateStatus(int oid, String status) {
        Connection conn = DBUtil.getConnection();
        String sql = "UPDATE [order] SET order_status=? WHERE order_id=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,status);pstmt.setInt(2,oid);
            int rows = pstmt.executeUpdate();DBUtil.close(conn,pstmt);return rows>0;
        } catch (SQLException e) {e.printStackTrace();return false;}
    }

    private List<OrderItem> findOrderItem(int oid) {
        List<OrderItem> list = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        String sql = "SELECT * FROM order_item WHERE order_id=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,oid);ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                OrderItem item = new OrderItem();
                item.setItemId(rs.getInt("item_id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setFlowerId(rs.getInt("flower_id"));
                item.setNum(rs.getInt("num"));
                item.setPrice(rs.getDouble("price"));
                list.add(item);
            }
            DBUtil.close(conn,pstmt,rs);
        } catch (SQLException e) {e.printStackTrace();}
        return list;
    }

    private Order getOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        order.setUserId(rs.getInt("user_id"));
        order.setOrderTime(rs.getDate("order_time"));
        order.setTotalPrice(rs.getDouble("total_price"));
        order.setOrderStatus(rs.getString("order_status"));
        order.setReceiver(rs.getString("receiver"));
        order.setPhone(rs.getString("phone"));
        order.setAddress(rs.getString("address"));
        return order;
    }
}