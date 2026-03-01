package com.oasis.atm;

import com.oasis.atm.model.User;
import com.oasis.atm.repository.FileRepo;
import com.oasis.atm.service.ATMService;
import com.oasis.atm.service.AuthService;

import java.util.Map;
import java.util.Scanner;

/**
 * Entry point for the ATM Interface application.
 * Handles the console menu flow and delegates to services.
 */
public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static FileRepo repo = new FileRepo();
    private static Map<String, User> users;
    private static AuthService authService;
    private static ATMService atmService;

    public static void main(String[] args) {
        users = repo.loadAll();
        authService = new AuthService(users, repo);
        atmService = new ATMService(users, repo);

        System.out.println("--------------------------------");
        System.out.println("║     Welcome to OASIS ATM     ║");
        System.out.println("---------------------------------");

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1 -> handleRegister();
                case 2 -> handleLogin();
                case 3 -> {
                    System.out.println("\n  Thank you for using OASIS ATM. Goodbye!");
                    running = false;
                }
                default -> System.out.println("  Invalid option. Try again.");
            }
        }
        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n  1. Register");
        System.out.println("  2. Login");
        System.out.println("  3. Exit");
    }

    private static void handleRegister() {
        System.out.print("\n  Enter User ID: ");
        String userId = scanner.nextLine().trim();
        System.out.print("  Set 4-digit PIN: ");
        String pin = scanner.nextLine().trim();
        authService.register(userId, pin);
    }

    private static void handleLogin() {
        System.out.print("\n  Enter User ID: ");
        String userId = scanner.nextLine().trim();
        System.out.print("  Enter PIN: ");
        String pin = scanner.nextLine().trim();

        if (!authService.login(userId, pin))
            return;

        System.out.println("\n  Login successful! Welcome, " + userId);
        handleDashboard(userId);
    }

    private static void handleDashboard(String userId) {
        boolean loggedIn = true;
        while (loggedIn) {
            printDashboard(userId);
            int choice = readInt("  Choose an option: ");
            switch (choice) {
                case 1 -> atmService.checkBalance(userId);
                case 2 -> {
                    double amt = readDouble("  Enter deposit amount: Rs.");
                    atmService.deposit(userId, amt);
                }
                case 3 -> {
                    double amt = readDouble("  Enter withdrawal amount: Rs.");
                    atmService.withdraw(userId, amt);
                }
                case 4 -> {
                    System.out.print("  Enter recipient User ID: ");
                    String to = scanner.nextLine().trim();
                    double amt = readDouble("  Enter transfer amount: Rs.");
                    atmService.transfer(userId, to, amt);
                }
                case 5 -> atmService.printTransactionHistory(userId);
                case 6 -> {
                    System.out.println("  Logged out successfully.");
                    loggedIn = false;
                }
                default -> System.out.println("  Invalid option.");
            }
        }
    }

    private static void printDashboard(String userId) {
        System.out.println("\n  --- ATM Dashboard [" + userId + "] ---------------");
        System.out.println("  │  1. Check Balance                 │");
        System.out.println("  │  2. Deposit                       │");
        System.out.println("  │  3. Withdraw                      │");
        System.out.println("  │  4. Transfer                      │");
        System.out.println("  │  5. Transaction History           │");
        System.out.println("  │  6. Logout                        │");
        System.out.println("  -------------------------------------");
    }

    // Helper: read integer safely
    private static int readInt(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Helper: read double safely
    private static double readDouble(String prompt) {
        System.out.print(prompt);
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("  [ERROR] Invalid amount.");
            return 0;
        }
    }
}
