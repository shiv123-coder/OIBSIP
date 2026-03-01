package com.oasis.atm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds the balance and transaction history for a user.
 * Demonstrates use of Collections API (ArrayList).
 */
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    private String accountId;
    private double balance;
    private List<Transaction> transactionHistory;

    public Account(String accountId) {
        this.accountId = accountId;
        this.balance = 1000.00; // Default opening balance
        this.transactionHistory = new ArrayList<>();
    }

    public String getAccountId() { return accountId; }
    public double getBalance() { return balance; }
    public List<Transaction> getTransactionHistory() { return transactionHistory; }

    public void credit(double amount) {
        this.balance += amount;
    }

    public void debit(double amount) {
        this.balance -= amount;
    }

    public void addTransaction(Transaction t) {
        transactionHistory.add(t);
    }
}
