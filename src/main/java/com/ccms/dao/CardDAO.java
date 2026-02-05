package com.ccms.dao;

import com.ccms.config.DBConnection;
import java.sql.*;

public class CardDAO {

    public int getCardIdByUser(int userId) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT id FROM credit_cards WHERE user_id=?"
            );
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean updateUsedAmount(int cardId, double amount) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE credit_cards SET used_amount = used_amount + ? WHERE id=?"
            );
            ps.setDouble(1, amount);
            ps.setInt(2, cardId);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean hasLimit(int cardId, double purchaseAmount) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT credit_limit, used_amount FROM credit_cards WHERE id=?"
            );
            ps.setInt(1, cardId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double limit = rs.getDouble("credit_limit");
                double used = rs.getDouble("used_amount");
                return (used + purchaseAmount) <= limit;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}