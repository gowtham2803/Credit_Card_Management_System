package com.ccms.servlet;

import com.ccms.service.TransactionService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/purchase")
public class PurchaseServlet extends HttpServlet {

    private TransactionService transactionService = new TransactionService();

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession(false);
        int userId = (int) session.getAttribute("userId");

        double amount = Double.parseDouble(req.getParameter("amount"));

        boolean result = transactionService.purchase(userId, amount);

        resp.getWriter().print(
                result ? "Purchase Successful" : "Purchase Failed"
        );
    }
}