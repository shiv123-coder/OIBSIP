package com.oasis.atm.service;

import com.oasis.atm.model.User;
import com.oasis.atm.repository.FileRepo;
import com.oasis.atm.security.AESUtil;

import java.util.Map;

/**
 * Handles user registration and PIN-based authentication.
 * PIN is never stored in plaintext — only AES-256 encrypted form is persisted.
 */
public class AuthService {

    private Map<String, User> users;
    private FileRepo repo;

    public AuthService(Map<String, User> users, FileRepo repo) {
        this.users = users;
        this.repo = repo;
    }

    /**
     * Registers a new user with an AES-encrypted PIN.
     * 
     * @return true if registration succeeded
     */
    public boolean register(String userId, String pin) {
        if (users.containsKey(userId)) {
            System.out.println("  [ERROR] User ID already exists.");
            return false;
        }
        try {
            String encryptedPin = AESUtil.encrypt(pin);
            User newUser = new User(userId, encryptedPin);
            users.put(userId, newUser);
            repo.saveAll(users);
            System.out.println("   Account created successfully for: " + userId);
            return true;
        } catch (Exception e) {
            System.err.println("  [ERROR] Registration failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Validates a PIN by decrypting the stored value and comparing.
     * 
     * @return true if credentials are valid
     */
    public boolean login(String userId, String pin) {
        User user = users.get(userId);
        if (user == null) {
            System.out.println("  [ERROR] User not found.");
            return false;
        }
        try {
            String decryptedPin = AESUtil.decrypt(user.getEncryptedPin());
            if (decryptedPin.equals(pin)) {
                return true;
            } else {
                System.out.println("  [ERROR] Incorrect PIN.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("  [ERROR] Authentication error: " + e.getMessage());
            return false;
        }
    }
}
