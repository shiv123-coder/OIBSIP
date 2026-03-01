# OASIS Java Projects — Internship Portfolio
# Submitted for Oasis Infobyte Java Development Internship
# Author: Shivshankar D. Mali
# Tech Focus: Core Java, OOP, File Handling, Encryption, JDBC, MySQL, Layered Architecture

--------------------------------------------------------------------------------

PROJECT 1: ATM Interface (Console-Based)

Tech Stack:
- Java 17+
- File I/O (Serialization)
- AES-256 Encryption (CBC + Random IV)
- Collections Framework

Folder Structure:
ATM_Interface/
├── src/com/oasis/atm/
│   ├── model/
│   ├── security/
│   ├── repository/
│   ├── service/
│   └── Main.java
├── screenshots/
└── data/ (auto-created)

How to Compile & Run (CMD):
cd ATM_Interface
javac -d out src/com/oasis/atm/**/*.java
java -cp out com.oasis.atm.Main

Key Features:
- Secure Login
- Balance Inquiry
- Deposit & Withdrawal
- Money Transfer
- Transaction History
- Persistent File Storage
- AES-256 Encrypted Credentials

OOP Concepts Demonstrated:
- Encapsulation → User, Account, Transaction
- Abstraction → ATMOperations Interface
- Polymorphism → ATMService implements ATMOperations
- Composition → ATMService uses FileRepo
- Exception Handling → Service & Repository layer
- File I/O → Object Streams
- Encryption → AESUtil
- Collections → ArrayList

Output Screenshots:
- Register & Login
- Balance, Deposit & Withdrawal
- Transfer, Transaction History & Exit

--------------------------------------------------------------------------------

PROJECT 2: Digital Library Management System (JDBC + MySQL)

Tech Stack:
- Java 17+
- JDBC (Prepared Statements)
- MySQL 8.x
- Java Date API
- Layered Architecture

Folder Structure:
Digital_Library/
├── src/com/oasis/library/
│   ├── model/
│   ├── dao/
│   ├── service/
│   ├── util/
│   └── controller/
├── resources/db_schema.sql
└── screenshots/

Setup Instructions:

1. Create Database:
mysql -u root -p < resources/db_schema.sql

2. Update DBConfig.java password:
private static final String PASSWORD = "your_password";

3. Add MySQL Connector JAR inside lib/

4. Compile & Run:
cd Digital_Library
javac -cp "lib/*" -d out src/com/oasis/library/**/*.java
java -cp "out;lib/*" com.oasis.library.controller.LibraryApp

Default Login Credentials:

Admin:
Email: Shiv@oasislibrary.com
Password: admin123

Member:
Email: Shankar5@mail.com
Password: Shankar123

Core Features:

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
- Send Query to Admin (Simulated Email)

Fine Calculation Logic:
- Loan Period: 14 Days
- Fine Rate: Rs.5 per overdue day
- Implemented in LibraryService.calculateFine()

OOP & JDBC Concepts Demonstrated:
- Abstraction → Abstract User class
- Inheritance → Admin & Member extend User
- Polymorphism → Overridden methods
- Encapsulation → Model classes
- Singleton Pattern → DBConfig
- Prepared Statements → SQL injection prevention
- Full CRUD → BookDAO
- Layered Architecture → model → dao → service → controller
- Date API → Fine calculation

Output Screenshots:
- Admin Login & Add Book
- View Books & Members
- Fine Report
- Active Transactions
- Member Register & Login
- Search & Issue Books
- Transactions & Pending Fines
- Query to Admin & Logout

--------------------------------------------------------------------------------

Conclusion:

This internship portfolio demonstrates:
- Strong OOP principles
- Secure file handling & encryption
- JDBC & MySQL integration
- Clean layered backend architecture
- Console-based enterprise system design

Developed as part of AICTE OIB-SIP Java Development Internship.