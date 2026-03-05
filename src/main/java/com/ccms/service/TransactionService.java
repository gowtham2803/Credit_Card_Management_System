package com.ccms.service;

import com.ccms.dao.CardDAO;
import com.ccms.dao.TransactionDAO;
import com.ccms.model.Transaction;
import com.ccms.util.ValidationUtil;

import java.util.List;

public class TransactionService {

    private CardDAO cardDAO = new CardDAO();
    private TransactionDAO txDAO = new TransactionDAO();

    public boolean purchase(int userId, double amount) {

        int cardId = cardDAO.getCardIdByUser(userId);
        if (cardId == -1) return false;

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

    public List<Transaction> getHistory(int userId) {
        return txDAO.getTransactionsByUser(userId);
    }

    public List<Transaction> getPaginatedTransactions(int userId, int page, int size) {
        return txDAO.getPaginatedTransactions(userId, page, size);
    }

    public int getTotalTransactionCount(int userId) {
        return txDAO.getTotalTransactionCount(userId);
    }

    public List<Transaction> getAllTransactions(int page, int size) {
        return txDAO.getAllTransactions(page, size);
    }
}