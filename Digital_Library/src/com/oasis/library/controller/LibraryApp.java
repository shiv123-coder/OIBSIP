package com.oasis.library.controller;

import com.oasis.library.model.Admin;
import com.oasis.library.model.Book;
import com.oasis.library.model.User;
import com.oasis.library.service.AuthService;
import com.oasis.library.service.EmailService;
import com.oasis.library.service.LibraryService;
import com.oasis.library.util.DBConfig;

import java.util.List;
import java.util.Scanner;

/**
 * Main entry point for the Digital Library System.
 * Handles all console menus and delegates to services.
 */
public class LibraryApp {

    private static Scanner scanner = new Scanner(System.in);
    private static AuthService authService = new AuthService();
    private static LibraryService libraryService = new LibraryService();
    private static EmailService emailService = new EmailService();

    public static void main(String[] args) {
        System.out.println("------------------------------------------");
        System.out.println("║   OASIS Digital Library Management     ║");
        System.out.println("------------------------------------------");

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("  Enter choice: ");
            switch (choice) {
                case 1 -> handleLogin();
                case 2 -> handleRegister();
                case 3 -> {
                    System.out.println("\n  Thank you for visiting OASIS Library. Goodbye!");
                    DBConfig.getInstance().closeConnection();
                    running = false;
                }
                default -> System.out.println("  Invalid choice.");
            }
        }
        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n  1. Login");
        System.out.println("  2. Register (New Member)");
        System.out.println("  3. Exit");
    }

    private static void handleLogin() {
        System.out.print("\n  Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("  Password: ");
        String password = scanner.nextLine().trim();

        User user = authService.login(email, password);
        if (user == null)
            return;

        if (user instanceof Admin) {
            handleAdminDashboard((Admin) user);
        } else {
            handleMemberDashboard(user);
        }
    }

    private static void handleRegister() {
        System.out.print("\n  Full Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("  Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("  Password: ");
        String password = scanner.nextLine().trim();
        authService.registerMember(name, email, password);
    }

    private static void handleAdminDashboard(Admin admin) {
        boolean active = true;
        while (active) {
            admin.displayMenu();
            int choice = readInt("  Choose an option: ");
            switch (choice) {
                case 1 -> addBook();
                case 2 -> updateBook();
                case 3 -> deleteBook();
                case 4 -> libraryService.printAllBooks();
                case 5 -> viewAllMembers();
                case 6 -> viewActiveTransactions();
                case 7 -> libraryService.printFineReport();
                case 8 -> {
                    System.out.println("  Logged out.");
                    active = false;
                }
                default -> System.out.println("  Invalid option.");
            }
        }
    }

    private static void addBook() {
        System.out.print("\n  Title: ");
        String title = scanner.nextLine().trim();
        System.out.print("  Author: ");
        String author = scanner.nextLine().trim();
        System.out.print("  Category: ");
        String category = scanner.nextLine().trim();
        int qty = readInt("  Quantity: ");
        libraryService.getBookDAO().addBook(new Book(0, title, author, category, qty));
    }

    private static void updateBook() {
        int id = readInt("\n  Enter Book ID to update: ");
        Book book = libraryService.getBookDAO().getBookById(id);
        if (book == null) {
            System.out.println("  Book not found.");
            return;
        }

        System.out.print("  New Title (" + book.getTitle() + "): ");
        String title = scanner.nextLine().trim();
        System.out.print("  New Author (" + book.getAuthor() + "): ");
        String author = scanner.nextLine().trim();
        System.out.print("  New Category (" + book.getCategory() + "): ");
        String category = scanner.nextLine().trim();

        if (!title.isEmpty())
            book.setTitle(title);
        if (!author.isEmpty())
            book.setAuthor(author);
        if (!category.isEmpty())
            book.setCategory(category);

        if (libraryService.getBookDAO().updateBook(book)) {
            System.out.println("   Book updated.");
        }
    }

    private static void deleteBook() {
        int id = readInt("\n  Enter Book ID to delete: ");
        if (libraryService.getBookDAO().deleteBook(id)) {
            System.out.println("   Book deleted.");
        } else {
            System.out.println("  [ERROR] Delete failed or book not found.");
        }
    }

    private static void viewAllMembers() {
        // Delegate to UserDAO via a new UserDAO instance for simplicity
        com.oasis.library.dao.UserDAO userDAO = new com.oasis.library.dao.UserDAO();
        List<User> members = userDAO.getAllMembers();
        System.out.println("\n  ---- All Members (" + members.size() + ") ----");
        members.forEach(m -> System.out.println("  " + m));
    }

    private static void viewActiveTransactions() {
        libraryService.printFineReport();
    }

    private static void handleMemberDashboard(User user) {
        boolean active = true;
        while (active) {
            user.displayMenu();
            int choice = readInt("  Choose an option: ");
            switch (choice) {
                case 1 -> {
                    System.out.print("  Search keyword: ");
                    String kw = scanner.nextLine().trim();
                    libraryService.searchBooks(kw);
                }
                case 2 -> {
                    int bookId = readInt("  Enter Book ID to issue: ");
                    libraryService.issueBook(user.getId(), bookId);
                }
                case 3 -> {
                    int bookId = readInt("  Enter Book ID to return: ");
                    libraryService.returnBook(user.getId(), bookId);
                }
                case 4 -> libraryService.printUserTransactions(user.getId());
                case 5 -> {
                    System.out.println("  (Checking pending fines...)");
                    libraryService.printUserTransactions(user.getId());
                }
                case 6 -> {
                    System.out.print("  Query subject: ");
                    String subject = scanner.nextLine().trim();
                    System.out.print("  Your message: ");
                    String msg = scanner.nextLine().trim();
                    emailService.sendQueryToAdmin(user.getEmail(), subject, msg);
                }
                case 7 -> {
                    System.out.println("  Logged out.");
                    active = false;
                }
                default -> System.out.println("  Invalid option.");
            }
        }
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
