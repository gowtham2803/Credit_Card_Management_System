package com.ccms.servlet;

import com.ccms.service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserService service = new UserService();
        int userId = service.login(email, password);

        if (userId > 0) {
            HttpSession session = req.getSession();
            session.setAttribute("userId", userId);
            resp.getWriter().print("Login Success");
        } else {
            resp.getWriter().print("Invalid Credentials");
        }
    }
}