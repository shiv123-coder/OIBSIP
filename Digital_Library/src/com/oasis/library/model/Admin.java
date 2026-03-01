package com.oasis.library.model;

/**
 * Admin user with full system access.
 * Demonstrates: Inheritance (extends User).
 */
public class Admin extends User {

    public Admin(int id, String name, String email, String password) {
        super(id, name, email, "ADMIN", password);
    }

    @Override
    public void displayMenu() {
        System.out.println("\n ------- Admin Dashboard -----------------");
        System.out.println("  │  1. Add Book                           │");
        System.out.println("  │  2. Update Book                        │");
        System.out.println("  │  3. Delete Book                        │");
        System.out.println("  │  4. View All Books                     │");
        System.out.println("  │  5. View All Members                   │");
        System.out.println("  │  6. View All Active Transactions       │");
        System.out.println("  │  7. Generate Fine Report               │");
        System.out.println("  │  8. Logout                             │");
        System.out.println("  ------------------------------------------");
    }
}
