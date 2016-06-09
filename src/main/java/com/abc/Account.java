package com.abc;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

    private final AccountType accountType;
    private List<Transaction> transactions;
    private double accountBalance;
    private long accountId ;


    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        deposit(amount, DateProvider.getInstance().now());
    }

    public void deposit(double amount, Date transactionDate) {
        if (amount <= 0) {
            throw new IllegalArgumentException("transactionAmount must be greater than zero");
        } else {
            accountBalance = accountBalance + amount;
            transactions.add(new Transaction(amount, transactionDate));
        }
    }

    public void withdraw(double amount) {
        withdraw(amount, DateProvider.getInstance().now());
    }

    public void withdraw(double amount, Date transactionDate) {
        if (amount <= 0) {
            throw new IllegalArgumentException("transactionAmount must be greater than zero");
        } else {
            accountBalance = accountBalance - amount;
            transactions.add(new Transaction(-amount, transactionDate));
        }
    }

    public double interestEarned() {

        switch(accountType){
            case SAVINGS:
                if (accountBalance <= 1000)
                    return accountBalance * 0.001;
                else
                    return 1 + (accountBalance-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (transactionAmount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:

                if(hasWithdrawalInPastTenDays()){
                    return accountBalance * 0.001;
                } else {
                    return accountBalance * 0.05;
                }
            default:
                return accountBalance * 0.001;
        }
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public boolean hasWithdrawalInPastTenDays(){
        LocalDateTime currentldt = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
        LocalDateTime transactionldt;
        for(Transaction transaction: transactions){
            if(transaction.transactionAmount < 0) {
                transactionldt = LocalDateTime.ofInstant(transaction.getTransactionDate().toInstant(), ZoneId.systemDefault());
                if (transactionldt.isAfter(currentldt.minusDays(10))) {
                    return true;
                }
            }
        }
        return false;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
