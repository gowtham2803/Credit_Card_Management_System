package com.ccms.servlet;

import com.ccms.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import com.ccms.util.PasswordUtil;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String rawPassword = req.getParameter("password");
        String password = PasswordUtil.hashPassword(rawPassword);

        UserService service = new UserService();
        boolean result = service.signup(name, email, password);

        resp.getWriter().print(result ? "Signup Successful" : "Signup Failed");
    }
}