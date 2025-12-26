package com.flower.servlet;
import com.flower.pojo.User;
import com.flower.service.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    private UserService service = new UserService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String code = req.getParameter("verifyCode");
        String sessionCode = (String) req.getSession().getAttribute("verifyCode");
        if(!code.equalsIgnoreCase(sessionCode)){
            req.setAttribute("msg","验证码错误！");
            req.getRequestDispatcher("/register.jsp").forward(req,resp);
            return;
        }
        User user = new User();
        user.setUsername(username);user.setPassword(password);
        user.setPhone(phone);user.setAddress(address);
        boolean res = service.register(user);
        if(res) resp.sendRedirect(req.getContextPath()+"/login.jsp");
        else {req.setAttribute("msg","用户名已存在！");req.getRequestDispatcher("/register.jsp").forward(req,resp);}
    }
}