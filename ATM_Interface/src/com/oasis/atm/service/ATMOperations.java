package com.oasis.atm.service;

/**
 * Defines the contract for all ATM operations.
 * Demonstrates: Abstraction via interface.
 */
public interface ATMOperations {
    void checkBalance(String userId);

    void deposit(String userId, double amount);

    void withdraw(String userId, double amount);

    void transfer(String fromUserId, String toUserId, double amount);

    void printTransactionHistory(String userId);
}
