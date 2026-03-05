package com.ccms.servlet;

import com.ccms.util.QRUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/generateQR")
public class QRServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("image/png");

        String txnId = req.getParameter("txnId");

        if (txnId == null) {
            resp.getWriter().print("Transaction ID required");
            return;
        }

        try {

            String qrText = "Transaction ID: " + txnId;

            QRUtil.generateQRCode(qrText, resp.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}