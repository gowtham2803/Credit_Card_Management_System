package com.ccms.service;

import com.ccms.dao.EmiDAO;

public class EmiService {

    private EmiDAO dao;

    public EmiService(EmiDAO dao) {
        this.dao = dao;
    }

    public EmiService() {
        this.dao = new EmiDAO();
    }

    public boolean convert(int txId, int months, double totalAmount) {

        if (months <= 0) {
            return false;
        }

        double monthly = totalAmount / months;

        return dao.convertToEmi(txId, months, monthly);
    }
}