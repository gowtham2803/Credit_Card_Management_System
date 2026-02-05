package com.ccms.servlet;

import com.ccms.service.TransactionService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/purchase")
public class PurchaseServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession(false);
        int userId = (int) session.getAttribute("userId");

        double amount = Double.parseDouble(req.getParameter("amount"));

        TransactionService service = new TransactionService();
        boolean result = service.purchase(userId, amount);

        resp.getWriter().print(
                result ? "Purchase Successful" : "Purchase Failed"
        );
    }
}