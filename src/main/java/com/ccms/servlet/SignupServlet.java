package com.ccms.servlet;

import com.ccms.service.UserService;
import com.ccms.util.PasswordUtil;
import com.ccms.util.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/plain");

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String rawPassword = req.getParameter("password");

        if (!ValidationUtil.isValidName(name)) {
            resp.getWriter().println("Invalid name format");
            return;
        }

        if (!ValidationUtil.isValidEmail(email)) {
            resp.getWriter().println("Invalid email format");
            return;
        }

        if (!ValidationUtil.isValidPassword(rawPassword)) {
            resp.getWriter().println("Password must contain uppercase, lowercase and number (min 8 chars)");
            return;
        }

        String password = PasswordUtil.hashPassword(rawPassword);

        UserService service = new UserService();
        boolean result = service.signup(name, email, password);

        if (result) {
            resp.getWriter().println("Signup Successful");
        } else {
            resp.getWriter().println("Signup Failed");
        }
    }
}