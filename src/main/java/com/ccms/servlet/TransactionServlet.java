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

        HttpSession session = req.getSession(false);
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