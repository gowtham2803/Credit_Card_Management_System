package com.ccms.servlet;

import com.ccms.model.User;
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
        User user = service.login(email, password);

        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("role", user.getRole());
            resp.getWriter().print("Login Success");
        } else {
            resp.getWriter().print("Invalid Credentials");
        }

    }
}