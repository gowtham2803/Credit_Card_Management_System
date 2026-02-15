package com.ccms.service;

import com.ccms.dao.UserDAO;
import com.ccms.model.User;

public class UserService {

    UserDAO dao = new UserDAO();

    // 🔹 Signup Logic
    public boolean signup(String name, String email, String password) {
        return dao.registerUser(name, email, password);
    }

    // 🔹 Login Logic (returns User object instead of int)
    public User login(String email, String password) {
        return dao.login(email, password);
    }
}
