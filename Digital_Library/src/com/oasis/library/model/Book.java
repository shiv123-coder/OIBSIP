package com.oasis.library.model;

/**
 * Represents a library book.
 * Demonstrates: Encapsulation.
 */
public class Book {
    private int id;
    private String title;
    private String author;
    private String category;
    private int quantity;
    private int availableCopies;

    public Book(int id, String title, String author, String category, int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.quantity = quantity;
        this.availableCopies = quantity;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    // Setters for updates
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public boolean isAvailable() {
        return availableCopies > 0;
    }

    @Override
    public String toString() {
        return String.format("Book[id=%d, title=%s, author=%s, category=%s, available=%d/%d]",
                id, title, author, category, availableCopies, quantity);
    }
}
