package com.flower.servlet;
import com.flower.pojo.Flower;
import com.flower.service.CartService;
import com.flower.service.FlowerService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/cartServlet")
public class CartServlet extends HttpServlet {
    private CartService cartService = new CartService();
    private FlowerService flowerService = new FlowerService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        int fid = Integer.parseInt(req.getParameter("fid"));
        if("add".equals(action)){
            int num = Integer.parseInt(req.getParameter("num"));
            List<Flower> list = flowerService.findAll();
            Optional<Flower> optional = list.stream().filter(f->f.getFlowerId()==fid).findFirst();
            if(optional.isPresent()) cartService.add(req.getSession(),optional.get(),num);
        }else if("remove".equals(action)){
            cartService.remove(req.getSession(),fid);
        }else if("update".equals(action)){
            int num = Integer.parseInt(req.getParameter("num"));
            cartService.updateNum(req.getSession(),fid,num);
        }else if("clear".equals(action)){
            cartService.clear(req.getSession());
        }
        resp.sendRedirect(req.getContextPath()+"/user/cart.jsp");
    }
}