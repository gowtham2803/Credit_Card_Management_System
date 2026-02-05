package com.ccms.servlet;

import com.ccms.config.DBConnection;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/billing")
public class BillingServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession(false);
        int userId = (int) session.getAttribute("userId");

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT used_amount FROM credit_cards WHERE user_id=?"
            );
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double due = rs.getDouble("used_amount");
                resp.getWriter().println("Monthly Bill Generated");
                resp.getWriter().println("Total Due Amount: " + due);
                resp.getWriter().println("Payment Due Date: 15th of this month");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}