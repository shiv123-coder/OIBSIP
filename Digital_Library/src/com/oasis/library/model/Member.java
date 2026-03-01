package com.oasis.library.model;

/**
 * Member user with limited access (borrow/return/search).
 */
public class Member extends User {

    public Member(int id, String name, String email, String password) {
        super(id, name, email, "MEMBER", password);
    }

    @Override
    public void displayMenu() {
        System.out.println("\n ------- Member Dashboard ------------------");
        System.out.println("  │  1. Search Books                       │");
        System.out.println("  │  2. Issue a Book                       │");
        System.out.println("  │  3. Return a Book                      │");
        System.out.println("  │  4. View My Transactions               │");
        System.out.println("  │  5. Check Pending Fines                │");
        System.out.println("  │  6. Send Query to Admin (Email)        │");
        System.out.println("  │  7. Logout                             │");
        System.out.println("  -------------------------------------------");
    }
}
