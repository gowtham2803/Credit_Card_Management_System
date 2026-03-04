package com.ccms.model;

import java.sql.Timestamp;

public class Transaction {

    private int id;
    private int cardId;
    private double amount;
    private Timestamp transactionDate;

    // 🔹 Getter & Setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // 🔹 Getter & Setter for cardId
    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    // 🔹 Getter & Setter for amount
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    // 🔹 Getter & Setter for transactionDate
    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }
}