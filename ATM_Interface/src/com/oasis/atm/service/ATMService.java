package com.oasis.atm.service;

import com.oasis.atm.model.Account;
import com.oasis.atm.model.Transaction;
import com.oasis.atm.model.User;
import com.oasis.atm.repository.FileRepo;

import java.util.List;
import java.util.Map;

/**
 * Implements all ATM business operations.
 * Demonstrates: Polymorphism (interface implementation), Composition (uses
 * FileRepo).
 */
public class ATMService implements ATMOperations {

    private Map<String, User> users;
    private FileRepo repo;

    public ATMService(Map<String, User> users, FileRepo repo) {
        this.users = users;
        this.repo = repo;
    }

    @Override
    public void checkBalance(String userId) {
        Account acc = getAccount(userId);
        System.out.printf("%n  Current Balance: Rs.%.2f%n", acc.getBalance());
    }

    @Override
    public void deposit(String userId, double amount) {
        if (amount <= 0) {
            System.out.println("  [ERROR] Deposit amount must be positive.");
            return;
        }
        Account acc = getAccount(userId);
        acc.credit(amount);
        acc.addTransaction(new Transaction(Transaction.Type.DEPOSIT, amount, "Cash deposit"));
        repo.saveAll(users);
        System.out.printf("   Rs.%.2f deposited successfully. New Balance: Rs.%.2f%n",
                amount, acc.getBalance());
    }

    @Override
    public void withdraw(String userId, double amount) {
        if (amount <= 0) {
            System.out.println("  [ERROR] Withdrawal amount must be positive.");
            return;
        }
        Account acc = getAccount(userId);

        // Custom exception handling for insufficient balance
        if (acc.getBalance() < amount) {
            System.out.printf("  [ERROR] Insufficient balance. Available: Rs.%.2f%n", acc.getBalance());
            return;
        }

        acc.debit(amount);
        acc.addTransaction(new Transaction(Transaction.Type.WITHDRAWAL, amount, "Cash withdrawal"));
        repo.saveAll(users);
        System.out.printf("   Rs.%.2f withdrawn successfully. New Balance: Rs.%.2f%n",
                amount, acc.getBalance());
    }

    @Override
    public void transfer(String fromUserId, String toUserId, double amount) {
        if (!users.containsKey(toUserId)) {
            System.out.println("  [ERROR] Recipient account not found: " + toUserId);
            return;
        }
        if (fromUserId.equals(toUserId)) {
            System.out.println("  [ERROR] Cannot transfer to yourself.");
            return;
        }

        Account from = getAccount(fromUserId);
        Account to = getAccount(toUserId);

        if (from.getBalance() < amount) {
            System.out.printf("  [ERROR] Insufficient balance. Available: Rs.%.2f%n", from.getBalance());
            return;
        }

        from.debit(amount);
        to.credit(amount);
        from.addTransaction(new Transaction(Transaction.Type.TRANSFER_OUT, amount, "Transfer to " + toUserId));
        to.addTransaction(new Transaction(Transaction.Type.TRANSFER_IN, amount, "Transfer from " + fromUserId));
        repo.saveAll(users);
        System.out.printf("   Rs.%.2f transferred to %s. Your Balance: Rs.%.2f%n",
                amount, toUserId, from.getBalance());
    }

    @Override
    public void printTransactionHistory(String userId) {
        List<Transaction> history = getAccount(userId).getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("  No transactions found.");
            return;
        }
        System.out.println("\n  ---- Transaction History ----");
        history.forEach(t -> System.out.println("  " + t));
        System.out.println("  ----------------------------");
    }

    // Helper: safely retrieve Account
    private Account getAccount(String userId) {
        return users.get(userId).getAccount();
    }
}
