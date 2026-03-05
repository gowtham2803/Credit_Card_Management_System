package com.ccms.servlet;

import com.ccms.model.User;
import com.ccms.service.UserService;
import com.ccms.util.PasswordUtil;
import com.ccms.util.ValidationUtil;
import com.ccms.util.EmailUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/plain");

        String email = req.getParameter("email");
        String rawPassword = req.getParameter("password");

        if (!ValidationUtil.isValidEmail(email)) {
            resp.getWriter().println("Invalid email format");
            return;
        }

        if (rawPassword == null || rawPassword.isEmpty()) {
            resp.getWriter().println("Password cannot be empty");
            return;
        }

        String password = PasswordUtil.hashPassword(rawPassword);

        UserService service = new UserService();
        User user = service.login(email, password);

        if (user != null) {

            HttpSession session = req.getSession(true);
            session.setAttribute("userId", user.getId());
            session.setAttribute("role", user.getRole());

            // Send login notification email
            String subject = "Login Alert - CCMS";
            String message = "Hello,\n\n"
                    + "Your account was successfully logged in.\n"
                    + "If this was not you, please contact support immediately.";

            EmailUtil.sendEmail("gowthamr280321@gmail.com", subject, message);

            resp.getWriter().println("Login Successful");

        } else {
            resp.getWriter().println("Invalid credentials");
        }
    }
}