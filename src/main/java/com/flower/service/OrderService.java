package com.flower.service;
import com.flower.dao.OrderDAO;
import com.flower.pojo.CartItem;
import com.flower.pojo.Order;
import com.flower.pojo.OrderItem;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private OrderDAO dao = new OrderDAO();
    private FlowerService flowerService = new FlowerService();
    private CartService cartService = new CartService();

    public boolean createOrder(HttpSession session, Order order) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if(cart==null || cart.isEmpty()) return false;
        List<OrderItem> itemList = new ArrayList<>();
        for(CartItem cartItem : cart){
            int fid = cartItem.getFlower().getFlowerId();int num=cartItem.getNum();
            if(!flowerService.reduceStock(fid,num)) return false;
            OrderItem item = new OrderItem();
            item.setFlowerId(fid);item.setNum(num);item.setPrice(cartItem.getFlower().getPrice());
            itemList.add(item);
        }
        order.setItemList(itemList);
        order.setTotalPrice(cartService.getTotal(session));
        order.setOrderStatus("待付款");
        boolean res = dao.addOrder(order);
        if(res) cartService.clear(session);
        return res;
    }
    public List<Order> findByUserId(int uid) {return dao.findByUserId(uid);}
    public boolean updateStatus(int oid, String status) {return dao.updateStatus(oid,status);}
}