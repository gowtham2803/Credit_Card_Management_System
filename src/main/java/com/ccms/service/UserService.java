package com.ccms.service;

import com.ccms.dao.UserDAO;
import com.ccms.model.User;

public class UserService {

    UserDAO dao = new UserDAO();

    public boolean signup(String name, String email, String password) {
        return dao.registerUser(name, email, password);
    }

    public User login(String email, String password) {
        return dao.login(email, password);
    }
}
