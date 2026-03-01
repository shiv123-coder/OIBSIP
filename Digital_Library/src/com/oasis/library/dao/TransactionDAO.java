package com.oasis.library.dao;

import com.oasis.library.model.Transaction;
import com.oasis.library.util.DBConfig;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for book issue/return transactions.
 * Demonstrates: JDBC with Date types, PreparedStatement.
 */
public class TransactionDAO {

    private Connection conn;

    public TransactionDAO() {
        this.conn = DBConfig.getInstance().getConnection();
    }

    /** Inserts a new issue record when a book is borrowed */
    public boolean issueBook(Transaction txn) {
        String sql = "INSERT INTO transactions (user_id, book_id, issue_date, fine) VALUES (?, ?, ?, 0)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, txn.getUserId());
            ps.setInt(2, txn.getBookId());
            ps.setDate(3, Date.valueOf(txn.getIssueDate()));
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("  [DAO ERROR] Issue book failed: " + e.getMessage());
            return false;
        }
    }

    /** Updates the return date and fine when a book is returned */
    public boolean returnBook(int transactionId, LocalDate returnDate, double fine) {
        String sql = "UPDATE transactions SET return_date = ?, fine = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(returnDate));
            ps.setDouble(2, fine);
            ps.setInt(3, transactionId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("  [DAO ERROR] Return book failed: " + e.getMessage());
            return false;
        }
    }

    /** Retrieves all transactions for a specific user */
    public List<Transaction> getTransactionsByUser(int userId) {
        List<Transaction> txns = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY issue_date DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                txns.add(mapResultToTransaction(rs));
            }
        } catch (SQLException e) {
            System.err.println("  [DAO ERROR] Get transactions failed: " + e.getMessage());
        }
        return txns;
    }

    /** Retrieves active (not yet returned) transaction for a user + book */
    public Transaction getActiveTransaction(int userId, int bookId) {
        String sql = "SELECT * FROM transactions WHERE user_id=? AND book_id=? AND return_date IS NULL";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapResultToTransaction(rs);
        } catch (SQLException e) {
            System.err.println("  [DAO ERROR] Get active transaction failed: " + e.getMessage());
        }
        return null;
    }

    /** All transactions with outstanding fines (for admin report) */
    public List<Transaction> getAllPendingFines() {
        List<Transaction> txns = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE return_date IS NULL";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                txns.add(mapResultToTransaction(rs));
            }
        } catch (SQLException e) {
            System.err.println("  [DAO ERROR] Get pending fines failed: " + e.getMessage());
        }
        return txns;
    }

    private Transaction mapResultToTransaction(ResultSet rs) throws SQLException {
        LocalDate issueDate = rs.getDate("issue_date").toLocalDate();
        Date returnDateSql = rs.getDate("return_date");
        LocalDate returnDate = (returnDateSql != null) ? returnDateSql.toLocalDate() : null;

        return new Transaction(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getInt("book_id"),
            issueDate,
            returnDate,
            rs.getDouble("fine")
        );
    }
}
