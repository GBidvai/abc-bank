package com.abc;

import java.util.Date;

public class Transaction {
    public final double transactionAmount;

    private Date transactionDate;

    public Transaction(double transactionAmount) {
        this.transactionAmount = transactionAmount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public Transaction(double transactionAmount, Date transactionDate) {
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
