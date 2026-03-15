package com.ccms.servlet;

import com.ccms.model.Transaction;
import com.ccms.service.TransactionService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/transactions")
public class TransactionServlet extends HttpServlet {

    private TransactionService transactionService = new TransactionService();

    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/plain");

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            resp.getWriter().print("Please login first");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        int page = 1;
        int size = 5;

        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }

        if (req.getParameter("size") != null) {
            size = Integer.parseInt(req.getParameter("size"));
        }

        List<Transaction> transactions =
                transactionService.getPaginatedTransactions(userId, page, size);

        if (transactions.isEmpty()) {
            resp.getWriter().println("No transactions found.");
            return;
        }

        for (Transaction tx : transactions) {
            resp.getWriter().println(
                    "Txn ID: " + tx.getId() +
                            " | Amount: " + tx.getAmount() +
                            " | Date: " + tx.getTransactionDate()
            );
        }
    }
}