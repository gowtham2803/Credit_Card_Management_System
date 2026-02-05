package com.ccms.dao;

import com.ccms.config.DBConnection;
import java.sql.*;

public class TransactionDAO {

    public int addTransaction(int cardId, double amount) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO transactions(card_id, amount) VALUES (?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1, cardId);
            ps.setDouble(2, amount);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    public ResultSet getTransactionsByUser(int userId) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT t.id, t.amount, t.transaction_date " +
                            "FROM transactions t " +
                            "JOIN credit_cards c ON t.card_id = c.id " +
                            "WHERE c.user_id = ?"
            );
            ps.setInt(1, userId);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}