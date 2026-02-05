package com.ccms.dao;

import com.ccms.config.DBConnection;
import java.sql.*;

public class EmiDAO {

    public boolean convertToEmi(int txId, int months, double monthlyAmt) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO emi(transaction_id, months, monthly_amount) VALUES (?,?,?)"
            );
            ps.setInt(1, txId);
            ps.setInt(2, months);
            ps.setDouble(3, monthlyAmt);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}