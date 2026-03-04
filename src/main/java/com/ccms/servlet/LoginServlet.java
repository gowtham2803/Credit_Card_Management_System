package com.ccms.servlet;

import com.ccms.model.User;
import com.ccms.service.UserService;
import com.ccms.util.PasswordUtil;
import com.ccms.util.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/plain");

        // 1️⃣ Get parameters
        String email = req.getParameter("email");
        String rawPassword = req.getParameter("password");

        // 2️⃣ Validate inputs
        if (!ValidationUtil.isValidEmail(email)) {
            resp.getWriter().println("Invalid email format");
            return;
        }

        if (rawPassword == null || rawPassword.isEmpty()) {
            resp.getWriter().println("Password cannot be empty");
            return;
        }

        // 3️⃣ Hash password
        String password = PasswordUtil.hashPassword(rawPassword);

        // 4️⃣ Login service
        UserService service = new UserService();
        User user = service.login(email, password);

        if (user != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("userId", user.getId());
            session.setAttribute("role", user.getRole());

            resp.getWriter().println("Login Successful");
        } else {
            resp.getWriter().println("Invalid credentials");
        }
    }
}