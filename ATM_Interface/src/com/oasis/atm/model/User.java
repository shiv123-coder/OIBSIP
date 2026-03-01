package com.oasis.atm.model;

import java.io.Serializable;

/**
 * Represents an ATM user with encrypted PIN storage.
 * Implements Serializable for file-based persistence.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String encryptedPin;  // AES-256 encrypted
    private Account account;

    public User(String userId, String encryptedPin) {
        this.userId = userId;
        this.encryptedPin = encryptedPin;
        this.account = new Account(userId);
    }

    // Getters & Setters
    public String getUserId() { return userId; }
    public String getEncryptedPin() { return encryptedPin; }
    public void setEncryptedPin(String encryptedPin) { this.encryptedPin = encryptedPin; }
    public Account getAccount() { return account; }

    @Override
    public String toString() {
        return "User{userId='" + userId + "', balance=" + account.getBalance() + "}";
    }
}
