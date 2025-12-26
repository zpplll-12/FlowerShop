package com.flower.service;
import com.flower.dao.UserDAO;
import com.flower.pojo.User;

public class UserService {
    private UserDAO dao = new UserDAO();
    public User login(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) return null;
        return dao.login(username, password);
    }
    public boolean register(User user) {
        if(dao.checkUsername(user.getUsername())) return false;
        return dao.register(user);
    }
    public boolean update(User user) {return dao.updateUser(user);}
}