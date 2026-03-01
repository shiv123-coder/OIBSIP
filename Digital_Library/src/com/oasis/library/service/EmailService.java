package com.oasis.library.service;

/**
 * Simulates email query submission from a member to admin.
 */
public class EmailService {

    private static final String ADMIN_EMAIL = "Shiv@oasislibrary.com";

    /**
     * Simulates sending a query email from a member to the admin.
     * 
     * @param fromEmail
     * @param subject
     * @param message
     */
    public void sendQueryToAdmin(String fromEmail, String subject, String message) {
        System.out.println("\n  ---- Simulated Email ----");
        System.out.println("  FROM   : " + fromEmail);
        System.out.println("  TO     : " + ADMIN_EMAIL);
        System.out.println("  SUBJECT: " + subject);
        System.out.println("  MESSAGE: " + message);
        System.out.println("   Query sent to admin (simulated).");
        System.out.println("  ------------------------");

    }
}
