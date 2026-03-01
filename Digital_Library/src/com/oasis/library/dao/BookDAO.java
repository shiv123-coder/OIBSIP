package com.oasis.library.dao;

import com.oasis.library.model.Book;
import com.oasis.library.util.DBConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for full Book CRUD operations.
 * Demonstrates: Full CRUD with PreparedStatement (INSERT, SELECT, UPDATE, DELETE).
 */
public class BookDAO {

    private Connection conn;

    public BookDAO() {
        this.conn = DBConfig.getInstance().getConnection();
    }

    /** INSERT: Add a new book */
    public boolean addBook(Book book) {
        String sql = "INSERT INTO books (title, author, category, quantity, available_copies) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setInt(4, book.getQuantity());
            ps.setInt(5, book.getAvailableCopies());
            ps.executeUpdate();
            System.out.println("  ✔ Book added: " + book.getTitle());
            return true;
        } catch (SQLException e) {
            System.err.println("  [DAO ERROR] Add book failed: " + e.getMessage());
            return false;
        }
    }

    /** SELECT: Find books by title or author (search) */
    public List<Book> searchBooks(String keyword) {
        List<Book> results = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                results.add(mapResultToBook(rs));
            }
        } catch (SQLException e) {
            System.err.println("  [DAO ERROR] Search failed: " + e.getMessage());
        }
        return results;
    }

    /** SELECT: Get all books */
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY title";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                books.add(mapResultToBook(rs));
            }
        } catch (SQLException e) {
            System.err.println("  [DAO ERROR] Get all books failed: " + e.getMessage());
        }
        return books;
    }

    /** SELECT: Get a single book by ID */
    public Book getBookById(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapResultToBook(rs);
        } catch (SQLException e) {
            System.err.println("  [DAO ERROR] Get book failed: " + e.getMessage());
        }
        return null;
    }

    /** UPDATE: Modify book details */
    public boolean updateBook(Book book) {
        String sql = "UPDATE books SET title=?, author=?, category=?, quantity=?, available_copies=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setInt(4, book.getQuantity());
            ps.setInt(5, book.getAvailableCopies());
            ps.setInt(6, book.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("  [DAO ERROR] Update book failed: " + e.getMessage());
            return false;
        }
    }

    /** DELETE: Remove a book by ID */
    public boolean deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("  [DAO ERROR] Delete book failed: " + e.getMessage());
            return false;
        }
    }

    private Book mapResultToBook(ResultSet rs) throws SQLException {
        return new Book(
            rs.getInt("id"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getString("category"),
            rs.getInt("quantity")
        );
    }
}
