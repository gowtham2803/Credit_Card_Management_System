package com.ccms.service;

import com.ccms.dao.CardDAO;
import com.ccms.dao.TransactionDAO;
import com.ccms.model.Transaction;
import com.ccms.util.ValidationUtil;

import java.util.List;

public class TransactionService {

    private CardDAO cardDAO;
    private TransactionDAO txDAO;

    public TransactionService(CardDAO cardDAO, TransactionDAO txDAO) {
        this.cardDAO = cardDAO;
        this.txDAO = txDAO;
    }

    public TransactionService() {
        this.cardDAO = new CardDAO();
        this.txDAO = new TransactionDAO();
    }

    public boolean purchase(int userId, double amount) {

        if (!ValidationUtil.isValidAmount(amount)) {
            return false;
        }

        int cardId = cardDAO.getCardIdByUser(userId);

        if (cardId == -1) {
            return false;
        }

        if (!cardDAO.hasLimit(cardId, amount)) {
            return false;
        }

        int txId = txDAO.addTransaction(cardId, amount);

        if (txId > 0) {
            return cardDAO.updateUsedAmount(cardId, amount);
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