package com.ccms.servlet;

import com.ccms.util.QRUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/generateQR")
public class QRServlet extends HttpServlet {

    protected void generateQR(String text, OutputStream out) throws Exception {
        QRUtil.generateQRCode(text, out);
    }

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

            generateQR(qrText, resp.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}