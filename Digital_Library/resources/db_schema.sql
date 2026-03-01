-- ============================================
-- OASIS Digital Library Management System
-- Database Schema
-- Run this FIRST before launching the app
-- ============================================

CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

-- ---- USERS TABLE ----
CREATE TABLE IF NOT EXISTS users (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    email    VARCHAR(100) NOT NULL UNIQUE,
    role     ENUM('ADMIN', 'MEMBER') NOT NULL DEFAULT 'MEMBER',
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ---- BOOKS TABLE ----
CREATE TABLE IF NOT EXISTS books (
    id               INT AUTO_INCREMENT PRIMARY KEY,
    title            VARCHAR(200) NOT NULL,
    author           VARCHAR(100) NOT NULL,
    category         VARCHAR(50),
    quantity         INT NOT NULL DEFAULT 1,
    available_copies INT NOT NULL DEFAULT 1,
    added_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ---- TRANSACTIONS TABLE ----
CREATE TABLE IF NOT EXISTS transactions (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT NOT NULL,
    book_id     INT NOT NULL,
    issue_date  DATE NOT NULL,
    return_date DATE,          -- NULL means not yet returned
    fine        DECIMAL(8, 2) DEFAULT 0.00,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);

-- ============================================
-- SEED DATA (Sample records for testing)
-- ============================================

-- Default admin account (password: admin123)
INSERT INTO users (name, email, role, password) VALUES
    ('Admin User', 'Shiv@oasislibrary.com', 'ADMIN', 'admin123');

-- Sample member accounts
INSERT INTO users (name, email, role, password) VALUES
    ('Shankar', 'Shankar5@mail.com', 'MEMBER', 'Shankar123'),
    ('Kamini',     'Kamini@mail.com',   'MEMBER', 'Kamu123');

-- Sample books
INSERT INTO books (title, author, category, quantity, available_copies) VALUES
    ('Clean Code',                    'Robert C. Martin',  'Programming',  3, 3),
    ('The Pragmatic Programmer',      'Andy Hunt',         'Programming',  2, 2),
    ('Design Patterns',               'Gang of Four',      'Architecture', 2, 2),
    ('Introduction to Algorithms',    'Cormen et al.',     'CS Theory',    4, 4),
    ('Database System Concepts',      'Silberschatz',      'Databases',    2, 2),
    ('Effective Java',                'Joshua Bloch',      'Java',         3, 3),
    ('Head First Java',               'Kathy Sierra',      'Java',         3, 3),
    ('The Alchemist',                 'Paulo Coelho',      'Fiction',      5, 5),
    ('Sapiens',                       'Yuval Noah Harari', 'Non-Fiction',  2, 2),
    ('Atomic Habits',                 'James Clear',       'Self-Help',    4, 4);
