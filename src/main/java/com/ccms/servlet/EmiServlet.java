package com.ccms.servlet;

import com.ccms.service.EmiService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/emi")
public class EmiServlet extends HttpServlet {

    private EmiService emiService = new EmiService();

    public void setEmiService(EmiService emiService) {
        this.emiService = emiService;
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int txId = Integer.parseInt(req.getParameter("txId"));
        int months = Integer.parseInt(req.getParameter("months"));
        double amount = Double.parseDouble(req.getParameter("amount"));

        boolean result = emiService.convert(txId, months, amount);

        resp.getWriter().print(
                result ? "EMI Converted Successfully" : "EMI Failed"
        );
    }
}