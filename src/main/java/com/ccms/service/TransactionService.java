package com.ccms.service;

import com.ccms.dao.CardDAO;
import com.ccms.dao.TransactionDAO;
import com.ccms.model.Transaction;
import com.ccms.util.ValidationUtil;

import java.util.List;

public class TransactionService {

    private CardDAO cardDAO = new CardDAO();
    private TransactionDAO txDAO = new TransactionDAO();

    // 🔹 Purchase Logic
    public boolean purchase(int userId, double amount) {

        int cardId = cardDAO.getCardIdByUser(userId);
        if (cardId == -1) return false;

        // Credit limit validation
        if (!cardDAO.hasLimit(cardId, amount)) {
            return false;
        }

        int txId = txDAO.addTransaction(cardId, amount);

        if (txId > 0) {
            return cardDAO.updateUsedAmount(cardId, amount);
        }
        if (!ValidationUtil.isValidAmount(amount)) {
            return false;
        }

        return false;
    }

    // 🔹 Get All Transactions for a User
    public List<Transaction> getHistory(int userId) {
        return txDAO.getTransactionsByUser(userId);
    }

    // 🔹 Pagination for User Transactions
    public List<Transaction> getPaginatedTransactions(int userId, int page, int size) {
        return txDAO.getPaginatedTransactions(userId, page, size);
    }

    // 🔹 Get Total Count (For Pagination)
    public int getTotalTransactionCount(int userId) {
        return txDAO.getTotalTransactionCount(userId);
    }

    // 🔹 Admin: Get All Transactions
    public List<Transaction> getAllTransactions(int page, int size) {
        return txDAO.getAllTransactions(page, size);
    }
}