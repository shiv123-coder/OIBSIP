package com.oasis.atm.repository;

import com.oasis.atm.model.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles reading and writing User data to an encrypted .dat file.
 * Demonstrates: File I/O, Serialization, Exception Handling.
 */
public class FileRepo {

    private static final String DATA_FILE = "data/users.dat";

    /**
     * Saves all users to the .dat file via Java serialization.
     */
    @SuppressWarnings("unchecked")
    public void saveAll(Map<String, User> users) {
        // Ensure the data directory exists
        new File("data").mkdirs();

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(DATA_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to save user data: " + e.getMessage());
        }
    }

    /**
     * Loads all users from the .dat file.
     * Returns an empty map if the file doesn't exist yet.
     */
    @SuppressWarnings("unchecked")
    public Map<String, User> loadAll() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return new HashMap<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(DATA_FILE))) {
            return (Map<String, User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[ERROR] Failed to load user data: " + e.getMessage());
            return new HashMap<>();
        }
    }
}
