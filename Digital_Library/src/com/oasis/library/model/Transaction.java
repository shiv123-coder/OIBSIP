package com.oasis.library.model;

import java.time.LocalDate;

/**
 * Represents a book issue/return transaction.
 * Demonstrates: Object Modeling, Java Date API.
 */
public class Transaction {
    private int id;
    private int userId;
    private int bookId;
    private LocalDate issueDate;
    private LocalDate returnDate;
    private LocalDate dueDate;
    private double fine;

    public static final int LOAN_PERIOD_DAYS = 14;
    public static final double FINE_PER_DAY = 5.0;

    public Transaction(int id, int userId, int bookId, LocalDate issueDate) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.dueDate = issueDate.plusDays(LOAN_PERIOD_DAYS);
        this.returnDate = null;
        this.fine = 0.0;
    }

    public Transaction(int id, int userId, int bookId,
            LocalDate issueDate, LocalDate returnDate, double fine) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.dueDate = issueDate.plusDays(LOAN_PERIOD_DAYS);
        this.fine = fine;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public double getFine() {
        return fine;
    }

    public boolean isReturned() {
        return returnDate != null;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    @Override
    public String toString() {
        String status = isReturned() ? "Returned on " + returnDate : "Due: " + dueDate;
        return String.format("Txn[id=%d, userId=%d, bookId=%d, issued=%s, %s, fine=Rs.%.2f]",
                id, userId, bookId, issueDate, status, fine);
    }
}
