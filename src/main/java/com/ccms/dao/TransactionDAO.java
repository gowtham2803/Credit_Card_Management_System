package com.ccms.dao;

import com.ccms.config.DBConnection;
import com.ccms.model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public int addTransaction(int cardId, double amount) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO transactions(card_id, amount) VALUES (?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, cardId);
            ps.setDouble(2, amount);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public List<Transaction> getTransactionsByUser(int userId) {

        List<Transaction> transactions = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT t.id, t.amount, t.transaction_date " +
                             "FROM transactions t " +
                             "JOIN credit_cards c ON t.card_id = c.id " +
                             "WHERE c.user_id = ?")) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Transaction tx = new Transaction();
                    tx.setId(rs.getInt("id"));
                    tx.setAmount(rs.getDouble("amount"));
                    tx.setTransactionDate(rs.getTimestamp("transaction_date"));
                    transactions.add(tx);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return transactions;
    }
    public List<Transaction> getPaginatedTransactions(int userId, int page, int size) {

        List<Transaction> transactions = new ArrayList<>();

        int offset = (page - 1) * size;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT t.id, t.amount, t.transaction_date " +
                             "FROM transactions t " +
                             "JOIN credit_cards c ON t.card_id = c.id " +
                             "WHERE c.user_id = ? " +
                             "LIMIT ? OFFSET ?")) {

            ps.setInt(1, userId);
            ps.setInt(2, size);
            ps.setInt(3, offset);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Transaction tx = new Transaction();
                    tx.setId(rs.getInt("id"));
                    tx.setAmount(rs.getDouble("amount"));
                    tx.setTransactionDate(rs.getTimestamp("transaction_date"));
                    transactions.add(tx);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return transactions;
    }
    public int getTotalTransactionCount(int userId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT COUNT(*) " +
                             "FROM transactions t " +
                             "JOIN credit_cards c ON t.card_id = c.id " +
                             "WHERE c.user_id = ?")) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
    public List<Transaction> getAllTransactions(int page, int size) {

        List<Transaction> transactions = new ArrayList<>();

        int offset = (page - 1) * size;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT id, amount, transaction_date " +
                             "FROM transactions " +
                             "ORDER BY transaction_date DESC " +
                             "LIMIT ? OFFSET ?")) {

            ps.setInt(1, size);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Transaction tx = new Transaction();
                    tx.setId(rs.getInt("id"));
                    tx.setAmount(rs.getDouble("amount"));
                    tx.setTransactionDate(rs.getTimestamp("transaction_date"));
                    transactions.add(tx);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return transactions;
    }

}