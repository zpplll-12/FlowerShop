package com.flower.servlet;
import com.flower.pojo.User;
import com.flower.service.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    private UserService service = new UserService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");
        User user = service.login(username,password);
        if(user!=null){
            HttpSession session = req.getSession();
            session.setAttribute("user",user);
            if("on".equals(remember)){
                Cookie c1 = new Cookie("username",username);
                Cookie c2 = new Cookie("password",password);
                c1.setMaxAge(7*24*60*60);c2.setMaxAge(7*24*60*60);
                resp.addCookie(c1);resp.addCookie(c2);
            }else{
                Cookie c1 = new Cookie("username","");
                Cookie c2 = new Cookie("password","");
                c1.setMaxAge(0);c2.setMaxAge(0);
                resp.addCookie(c1);resp.addCookie(c2);
            }
            resp.sendRedirect(req.getContextPath()+"/index.jsp");
        }else{
            req.setAttribute("msg","用户名或密码错误！");
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }
    }
}