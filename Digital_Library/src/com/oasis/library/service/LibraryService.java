package com.oasis.library.service;

import com.oasis.library.dao.BookDAO;
import com.oasis.library.dao.TransactionDAO;
import com.oasis.library.model.Book;
import com.oasis.library.model.Transaction;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Contains all library business rules:
 * - Book issue/return workflow
 * - Fine calculation using Java Date API
 * - Admin reporting
 *
 * Business logic is kept here, NOT in the DAO layer.
 */
public class LibraryService {

    private BookDAO bookDAO;
    private TransactionDAO transactionDAO;

    public LibraryService() {
        this.bookDAO = new BookDAO();
        this.transactionDAO = new TransactionDAO();
    }

    /**
     * Issues a book to a member.
     * Checks availability, decrements copies, records transaction.
     */
    public boolean issueBook(int userId, int bookId) {
        Book book = bookDAO.getBookById(bookId);
        if (book == null) {
            System.out.println("  [ERROR] Book not found.");
            return false;
        }
        if (!book.isAvailable()) {
            System.out.println("  [ERROR] No copies available for: " + book.getTitle());
            return false;
        }

        // Decrement available copies
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookDAO.updateBook(book);

        // Create transaction record
        Transaction txn = new Transaction(0, userId, bookId, LocalDate.now());
        transactionDAO.issueBook(txn);

        System.out.println("   Book issued: \"" + book.getTitle() + "\"");
        System.out.println("   Due date: " + txn.getDueDate());
        return true;
    }

    /**
     * Processes a book return.
     * Calculates fine if overdue, updates records.
     */
    public boolean returnBook(int userId, int bookId) {
        Transaction txn = transactionDAO.getActiveTransaction(userId, bookId);
        if (txn == null) {
            System.out.println("  [ERROR] No active issue found for this book.");
            return false;
        }

        LocalDate today = LocalDate.now();
        double fine = calculateFine(txn.getDueDate(), today);

        // Update transaction with return date and fine
        transactionDAO.returnBook(txn.getId(), today, fine);

        // Restore available copy
        Book book = bookDAO.getBookById(bookId);
        if (book != null) {
            book.setAvailableCopies(book.getAvailableCopies() + 1);
            bookDAO.updateBook(book);
        }

        if (fine > 0) {
            System.out.printf("   Book returned late. Fine to pay: Rs.%.2f%n", fine);
        } else {
            System.out.println("   Book returned on time. No fine.");
        }
        return true;
    }

    /**
     * Calculates overdue fine.
     * Fine = ₹5 per day past due date.
     */
    public double calculateFine(LocalDate dueDate, LocalDate returnDate) {
        if (!returnDate.isAfter(dueDate))
            return 0.0;
        long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
        return daysLate * Transaction.FINE_PER_DAY;
    }

    /** Print all transactions for a user */
    public void printUserTransactions(int userId) {
        List<Transaction> txns = transactionDAO.getTransactionsByUser(userId);
        if (txns.isEmpty()) {
            System.out.println("  No transaction history found.");
            return;
        }
        System.out.println("\n  ---- Your Transaction History ----");
        txns.forEach(t -> System.out.println("  " + t));
        System.out.println("  ----------------------------------");
    }

    /** Print all books with outstanding fines (admin report) */
    public void printFineReport() {
        List<Transaction> pending = transactionDAO.getAllPendingFines();
        System.out.println("\n  ---- Pending Fines Report ----");
        double total = 0;
        LocalDate today = LocalDate.now();
        for (Transaction t : pending) {
            double currentFine = calculateFine(t.getDueDate(), today);
            total += currentFine;
            if (currentFine > 0) {
                System.out.printf("  Txn ID: %d | User: %d | Book: %d | Fine: Rs.%.2f%n",
                        t.getId(), t.getUserId(), t.getBookId(), currentFine);
            }
        }
        System.out.printf("  Total outstanding fines: Rs.%.2f%n", total);
        System.out.println("  ------------------------------");
    }

    /** Display all books */
    public void printAllBooks() {
        List<Book> books = bookDAO.getAllBooks();
        System.out.println("\n  ---- Book Catalog (" + books.size() + " books) ----");
        books.forEach(b -> System.out.printf(
                "  [%d] %-40s | %-20s | %-15s | Copies: %d/%d%n",
                b.getId(), b.getTitle(), b.getAuthor(), b.getCategory(),
                b.getAvailableCopies(), b.getQuantity()));
    }

    /** Search books by keyword */
    public void searchBooks(String keyword) {
        List<Book> results = bookDAO.searchBooks(keyword);
        if (results.isEmpty()) {
            System.out.println("  No books found for: \"" + keyword + "\"");
            return;
        }
        System.out.println("\n  Search Results:");
        results.forEach(b -> System.out.println("  " + b));
    }

    public BookDAO getBookDAO() {
        return bookDAO;
    }
}
