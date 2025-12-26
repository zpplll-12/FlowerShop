package com.flower.servlet;
import com.flower.pojo.Order;
import com.flower.pojo.User;
import com.flower.service.OrderService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/orderServlet")
public class OrderServlet extends HttpServlet {
    private OrderService service = new OrderService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if("create".equals(action)){
            String receiver = req.getParameter("receiver");
            String phone = req.getParameter("phone");
            String address = req.getParameter("address");
            Order order = new Order();
            order.setUserId(user.getUserId());
            order.setReceiver(receiver);
            order.setPhone(phone);
            order.setAddress(address);
            boolean res = service.createOrder(session,order);
            if(res) resp.sendRedirect(req.getContextPath()+"/user/order_list.jsp");
            else {req.setAttribute("msg","下单失败，库存不足！");req.getRequestDispatcher("/user/cart.jsp").forward(req,resp);}
        }else if("updateStatus".equals(action)){
            int oid = Integer.parseInt(req.getParameter("oid"));
            String status = req.getParameter("status");
            service.updateStatus(oid,status);
            resp.sendRedirect(req.getContextPath()+"/user/order_list.jsp");
        }
    }
}