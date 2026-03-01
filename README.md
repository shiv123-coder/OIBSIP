# 🚀 OASIS Java Projects — Internship Portfolio

<p align="center">
  <img src="https://img.shields.io/badge/Java-17+-red?style=for-the-badge&logo=java" />
  <img src="https://img.shields.io/badge/MySQL-blue?style=for-the-badge&logo=mysql" />
  <img src="https://img.shields.io/badge/JDBC-PreparedStatements-green?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Security-AES--256-orange?style=for-the-badge" />
</p>

---

## 👨‍💻 Author
Shivshankar D. Mali  
Submitted for Oasis Infobyte Java Development Internship (OIB-SIP)

---

# 📌 About This Portfolio

This repository contains two complete Java backend systems demonstrating:

- Strong OOP Principles
- Secure File Handling & Encryption
- JDBC + MySQL Integration
- Layered Architecture Design
- Console-Based Enterprise Application Development

---

# 🏦 Project 1: ATM Interface (Console-Based)

## 🔹 Tech Stack
- Java 17+
- File I/O (Serialization)
- AES-256 Encryption (CBC + Random IV)
- Collections Framework

---

## 📂 Folder Structure

ATM_Interface/
│
├── src/com/oasis/atm/
│   ├── model/
│   ├── security/
│   ├── repository/
│   ├── service/
│   └── Main.java
│
├── screenshots/
└── data/ (auto-created)

---

## ▶️ How to Compile & Run

cd ATM_Interface
javac -d out src/com/oasis/atm/**/*.java
java -cp out com.oasis.atm.Main

---

## ⭐ Key Features

- Secure Login
- Balance Inquiry
- Deposit & Withdrawal
- Money Transfer
- Transaction History
- Persistent File Storage
- AES-256 Encrypted Credentials

---

## 🧠 OOP Concepts Implemented

- Encapsulation → User, Account, Transaction
- Abstraction → ATMOperations Interface
- Polymorphism → ATMService implements ATMOperations
- Composition → ATMService uses FileRepo
- Exception Handling → Service & Repository Layers
- Collections → ArrayList

---

# 📚 Project 2: Digital Library Management System (JDBC + MySQL)

## 🔹 Tech Stack
- Java 17+
- JDBC (Prepared Statements)
- MySQL 8.x
- Java Date API
- Layered Architecture

---

## 📂 Folder Structure

Digital_Library/
│
├── src/com/oasis/library/
│   ├── model/
│   ├── dao/
│   ├── service/
│   ├── util/
│   └── controller/
│
├── resources/db_schema.sql
└── screenshots/

---

## ⚙️ Setup Instructions

1. Create Database

mysql -u root -p < resources/db_schema.sql

2. Update DB Password

Edit DBConfig.java:

private static final String PASSWORD = "your_password";

3. Add MySQL Connector

Place MySQL Connector JAR inside lib/

---

## ▶️ Compile & Run

cd Digital_Library
javac -cp "lib/*" -d out src/com/oasis/library/**/*.java
java -cp "out;lib/*" com.oasis.library.controller.LibraryApp

---

## 🔐 Default Login Credentials

Admin:
Email: Shiv@oasislibrary.com
Password: admin123

Member:
Email: Shankar5@mail.com
Password: Shankar123

---

## ⭐ Core Features

Admin Module:
- Add / Update / Delete Books
- View All Books
- View All Members
- View Active Transactions
- Generate Fine Report

Member Module:
- Register & Login
- Search Books
- Issue Book
- Return Book
- View Transactions
- Check Pending Fines
- Send Query to Admin

---

## 💰 Fine Calculation Logic

Loan Period: 14 Days  
Fine Rate: ₹5 per overdue day  
Implemented in: LibraryService.calculateFine()

---

## 🧠 OOP & JDBC Concepts Demonstrated

- Abstraction → Abstract User class
- Inheritance → Admin & Member extend User
- Polymorphism → Overridden methods
- Encapsulation → Model classes
- Singleton Pattern → DBConfig
- Prepared Statements → SQL Injection Prevention
- Full CRUD Operations → BookDAO
- Layered Architecture → model → dao → service → controller

---

# 🎯 Internship Outcome

This internship portfolio demonstrates:

- Enterprise-Level Console Application Design
- Secure Authentication & Encryption
- Real-World Database Integration
- Clean Code & Structured Architecture
- Strong Core Java & JDBC Understanding

---

⭐ If you found this project useful, consider giving it a star!
