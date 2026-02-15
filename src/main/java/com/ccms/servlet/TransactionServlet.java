package com.ccms.servlet;

import com.ccms.service.TransactionService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.ResultSet;

@WebServlet("/transactions")
public class TransactionServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/plain");

        // 1️⃣ Check session
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().print("Please login first.");
            return;
        }

        // 2️⃣ Role-based authorization
        String role = (String) session.getAttribute("role");

        if (!"ADMIN".equals(role)) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().print("Access denied. Admin only.");
            return;
        }

        // 3️⃣ Admin allowed → fetch all transactions
        int userId = (int) session.getAttribute("userId");

        TransactionService service = new TransactionService();
        ResultSet rs = service.getHistory(userId);

        try {
            while (rs.next()) {
                resp.getWriter().println(
                        "Txn ID: " + rs.getInt("id") +
                                " | Amount: " + rs.getDouble("amount") +
                                " | Date: " + rs.getTimestamp("transaction_date")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
