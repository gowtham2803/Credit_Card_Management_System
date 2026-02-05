package com.ccms.service;

import com.ccms.dao.UserDAO;

public class UserService {

    UserDAO dao = new UserDAO();

    public boolean signup(String name, String email, String password) {
        return dao.registerUser(name, email, password);
    }
    public int login(String email, String password) {
        return dao.login(email, password);
    }
}