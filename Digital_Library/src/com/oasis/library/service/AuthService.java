package com.oasis.library.service;

import com.oasis.library.dao.UserDAO;
import com.oasis.library.model.Admin;
import com.oasis.library.model.Member;
import com.oasis.library.model.User;

/**
 * Handles user login and registration.
 * Demonstrates: Service layer separating auth logic from DAO.
 */
public class AuthService {

    private UserDAO userDAO;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    /**
     * Authenticates a user by email + password.
     * 
     * @return User object (Admin or Member) or null on failure.
     */
    public User login(String email, String password) {
        User user = userDAO.findByEmailAndPassword(email, password);
        if (user != null) {
            System.out.println("   Welcome, " + user.getName() + " [" + user.getRole() + "]");
        } else {
            System.out.println("  [ERROR] Invalid email or password.");
        }
        return user;
    }

    /**
     * Registers a new MEMBER account.
     * 
     * @return true if registration succeeded.
     */
    public boolean registerMember(String name, String email, String password) {
        Member member = new Member(0, name, email, password);
        boolean success = userDAO.insertUser(member);
        if (success) {
            System.out.println("   Member account created for: " + name);
        }
        return success;
    }
}
