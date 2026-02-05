package com.ccms.service;

import com.ccms.dao.EmiDAO;

public class EmiService {

    EmiDAO dao = new EmiDAO();

    public boolean convert(int txId, int months, double totalAmount) {
        double monthly = totalAmount / months;
        return dao.convertToEmi(txId, months, monthly);
    }
}