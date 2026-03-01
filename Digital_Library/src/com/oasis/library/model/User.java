package com.oasis.library.model;

/**
 * Abstract base class for all library users.
 * Demonstrates: Abstraction + Inheritance foundation.
 */
public abstract class User {
    protected int id;
    protected String name;
    protected String email;
    protected String role;
    protected String password;

    public User(int id, String name, String email, String role, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    public abstract void displayMenu();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return String.format("User[id=%d, name=%s, role=%s, email=%s]", id, name, role, email);
    }
}
