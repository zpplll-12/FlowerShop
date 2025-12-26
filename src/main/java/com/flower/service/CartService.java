package com.flower.service;
import com.flower.pojo.CartItem;
import com.flower.pojo.Flower;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class CartService {
    public void add(HttpSession session, Flower flower, int num) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if(cart == null) cart = new ArrayList<>();
        boolean isExist = false;
        for(CartItem item : cart){
            if(item.getFlower().getFlowerId() == flower.getFlowerId()){
                item.setNum(item.getNum()+num);isExist=true;break;
            }
        }
        if(!isExist) cart.add(new CartItem(flower,num));
        session.setAttribute("cart",cart);
    }
    public void remove(HttpSession session, int fid) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if(cart!=null) cart.removeIf(item->item.getFlower().getFlowerId()==fid);
        session.setAttribute("cart",cart);
    }
    public void updateNum(HttpSession session, int fid, int num) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if(cart!=null && num>0){
            for(CartItem item : cart){
                if(item.getFlower().getFlowerId()==fid){item.setNum(num);break;}
            }
        }
        session.setAttribute("cart",cart);
    }
    public void clear(HttpSession session) {session.removeAttribute("cart");}
    public double getTotal(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        double total=0;if(cart!=null) for(CartItem item : cart) total+=item.getSubTotal();
        return total;
    }
}