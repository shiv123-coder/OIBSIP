# OASIS Java Projects — Internship Portfolio
## Project 1: ATM Interface | Project 2: Digital Library

---

## PROJECT 1: ATM Interface (Console-Based)

### Tech Stack
- Core Java 17+, File I/O, AES-256 Encryption, Serialization

### Folder Structure
```
ATM_Interface/
├── src/com/oasis/atm/
│   ├── model/         → User.java, Account.java, Transaction.java
│   ├── security/      → AESUtil.java
│   ├── repository/    → FileRepo.java
│   ├── service/       → ATMOperations.java (interface), ATMService.java, AuthService.java
│   └── Main.java
└── data/
    └── users.dat      (auto-created on first run)
```

### How to Compile & Run
```bash
cd ATM_Interface
javac -d out (Get-ChildItem -Recurse -Filter *.java src | % FullName)
java -cp out com.oasis.atm.Main
```

### OOP Concepts Map
| Concept           | Where                      |
|-------------------|----------------------------|
| Encapsulation     | User, Account, Transaction |
| Abstraction       | ATMOperations interface     |
| Polymorphism      | ATMService implements ATMOperations |
| Composition       | ATMService uses FileRepo    |
| Exception Handling| FileRepo, ATMService        |
| File I/O          | FileRepo (ObjectStream)     |
| Encryption        | AESUtil (AES-256 CBC + random IV) |
| Collections       | ArrayList in Account        |

---

## PROJECT 2: Digital Library (JDBC + MySQL)

### Tech Stack
- Core Java 17+, JDBC, MySQL 8.x, Java Date API

### Folder Structure
```
Digital_Library/
├── src/com/oasis/library/
│   ├── model/      → User.java (abstract), Admin.java, Member.java, Book.java, Transaction.java
│   ├── dao/        → UserDAO.java, BookDAO.java, TransactionDAO.java
│   ├── service/    → LibraryService.java, AuthService.java, EmailService.java
│   ├── util/       → DBConfig.java (Singleton)
│   └── controller/ → LibraryApp.java
└── resources/
    └── db_schema.sql
```

### Setup Steps
1. Install MySQL and create the database:
   ```bash
   mysql -u root -p < resources/db_schema.sql
   ```

2. Update DB password in `DBConfig.java`:
   ```java
   private static final String PASSWORD = "your_actual_password";
   ```

3. Download MySQL Connector/J JAR from:
   https://dev.mysql.com/downloads/connector/j/

4. Compile and run:
   ```bash
   cd Digital_Library
   javac -cp "lib/*" -d out (Get-ChildItem -Recurse -Filter *.java src | % FullName)
   java -cp "out;lib/*" com.oasis.library.controller.LibraryApp
### Default Login Credentials
| Role   | Email                      | Password  |
|--------|----------------------------|-----------|
| Admin  | Shiv@oasislibrary.com     | admin123  |
| Member | Shankar5@mail.com             | Shankar123  |
| Member | Kamini@mail.com               | Kamu123    |

### OOP & JDBC Concepts Map
| Concept           | Where                           |
|-------------------|---------------------------------|
| Abstraction       | Abstract User class             |
| Inheritance       | Admin, Member extend User       |
| Polymorphism      | displayMenu() overridden        |
| Encapsulation     | All model classes               |
| Singleton Pattern | DBConfig                        |
| JDBC PreparedStmt | All DAO classes (SQL injection prevention) |
| Full CRUD         | BookDAO (INSERT/SELECT/UPDATE/DELETE) |
| Java Date API     | Fine calculation in LibraryService |
| Layered Design    | model → dao → service → controller |

### Fine Calculation Logic
- Loan period: 14 days
- Fine rate: ₹5 per overdue day
- Logic lives in `LibraryService.calculateFine()` — NOT in the DAO

---

## Interview Tips

**Q: Why file storage for ATM but MySQL for Library?**
> ATM is a standalone, single-user machine — no network needed, file serialization is sufficient. Library has relational data (users borrowing books, transactions with foreign keys) that benefits from SQL joins and structured queries.

**Q: Why random IV in AESUtil?**
> A static IV means identical PINs encrypt to identical ciphertext — an attacker can tell two users share the same PIN without decrypting. A random IV per encryption guarantees the ciphertext is unique every time.

**Q: Why is fine calculation in LibraryService instead of TransactionDAO?**
> DAOs should only handle persistence (CRUD). Business rules like "₹5/day after 14 days" are domain logic and belong in the service layer, making them easy to change without touching database code.
