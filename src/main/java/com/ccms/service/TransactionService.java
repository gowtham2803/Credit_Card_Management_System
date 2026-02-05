package com.ccms.service;

import com.ccms.dao.CardDAO;
import com.ccms.dao.TransactionDAO;

import java.sql.ResultSet;

public class TransactionService {

    CardDAO cardDAO = new CardDAO();
    TransactionDAO txDAO = new TransactionDAO();

    public boolean purchase(int userId, double amount) {

        int cardId = cardDAO.getCardIdByUser(userId);
        if (cardId == -1) return false;

        // Credit limit check
        if (!cardDAO.hasLimit(cardId, amount)) {
            return false;
        }

        int txId = txDAO.addTransaction(cardId, amount);
        if (txId > 0) {
            return cardDAO.updateUsedAmount(cardId, amount);
        }
        return false;
    }
    public ResultSet getHistory(int userId) {
        return txDAO.getTransactionsByUser(userId);
    }
}