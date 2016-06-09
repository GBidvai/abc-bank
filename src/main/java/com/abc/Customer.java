package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.getAccountBalance();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }


    public void transferFunds(long sourceAccountId , long destinationAccountId, double transactionAmount) throws Exception {
        Account sourceAccount = null;
        Account destinationAccount = null;
        for(Account account : accounts){
            if(account.getAccountId() == sourceAccountId){
                sourceAccount = account;
            }
            if(account.getAccountId() == destinationAccountId){
                destinationAccount = account;
            }

            if(sourceAccount != null && destinationAccount != null){
                break; // We found both source and destination accounts , no need for further iterations
            }

        }
        if(sourceAccount == null || destinationAccount == null){
            throw new Exception("Please enter valid source and destination account numbers");
        }
        if(transactionAmount > sourceAccount.getAccountBalance()){
            throw new Exception("Insufficient funds in account");
        }

        sourceAccount.withdraw(transactionAmount);
        destinationAccount.deposit(transactionAmount);
    }

    private String statementForAccount(Account a) {

        String s  = a.getAccountType().name();

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.getTransactions()) {
            s += "  " + (t.transactionAmount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.transactionAmount) + "\n";
            //total += t.transactionAmount;
        }
        s += "Total " + toDollars(a.getAccountBalance());
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
