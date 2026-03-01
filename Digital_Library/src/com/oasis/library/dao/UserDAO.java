package com.oasis.library.dao;

import com.oasis.library.model.Admin;
import com.oasis.library.model.Member;
import com.oasis.library.model.User;
import com.oasis.library.util.DBConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for User CRUD operations.
 * Demonstrates: PreparedStatement (SQL Injection prevention), JDBC, CRUD.
 */
public class UserDAO {

    private Connection conn;

    public UserDAO() {
        this.conn = DBConfig.getInstance().getConnection();
    }

    /**
     * Inserts a new user into the database.
     */
    public boolean insertUser(User user) {
        String sql = "INSERT INTO users (name, email, role, password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getRole());
            ps.setString(4, user.getPassword());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("  [DAO ERROR] Insert user failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Finds a user by email and password (for login).
     * 
     * @return User object or null if not found.
     */
    public User findByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultToUser(rs);
            }
        } catch (SQLException e) {
            System.err.println("  [DAO ERROR] Login query failed: " + e.getMessage());
        }
        return null;
    }

    /**
     * Returns all MEMBER-role users.
     */
    public List<User> getAllMembers() {
        List<User> members = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 'MEMBER'";
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                members.add(mapResultToUser(rs));
            }
        } catch (SQLException e) {
            System.err.println("  [DAO ERROR] Get members failed: " + e.getMessage());
        }
        return members;
    }

    private User mapResultToUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String role = rs.getString("role");
        String password = rs.getString("password");

        if ("ADMIN".equals(role)) {
            return new Admin(id, name, email, password);
        } else {
            return new Member(id, name, email, password);
        }
    }
}
