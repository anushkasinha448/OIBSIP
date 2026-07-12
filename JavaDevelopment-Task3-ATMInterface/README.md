# 🏧 ATM Interface

## Oasis Infobyte Java Development Internship

### Task 3 – ATM Interface

---

## 📖 Project Overview

The **ATM Interface** is a desktop banking application developed using **Java Swing** and **JDBC** with an **Oracle Database**. It simulates the core functionalities of an Automated Teller Machine (ATM), allowing users to securely log in, check their account balance, deposit money, withdraw cash, transfer funds, and view transaction history through an intuitive graphical user interface.

This project was developed as **Task 3** of the **Oasis Infobyte Java Development Internship**.

---

## ✨ Features

### 🔐 Secure Login
- User authentication using Account Number and PIN
- Prevents unauthorized access
- Input validation for login credentials

### 💰 Deposit Money
- Deposit funds into the account
- Automatically updates account balance
- Stores the transaction in the database

### 💵 Withdraw Money
- Withdraw available balance
- Prevents withdrawal when funds are insufficient
- Updates balance after successful withdrawal

### 🔄 Fund Transfer
- Transfer money to another valid account
- Validates receiver account
- Updates sender and receiver balances
- Records the transaction

### 📊 Balance Enquiry
- Displays the current available account balance

### 📜 Transaction History
- Displays all transactions performed by the logged-in user
- Shows:
  - Transaction Type
  - Amount
  - Updated Balance
  - Date and Time

### ✅ Input Validation
- Prevents empty fields
- Validates numeric inputs
- Ensures valid transaction amounts
- Displays appropriate error messages

---

## 🛠 Technologies Used

- Java
- Java Swing
- JDBC
- Oracle Database
- Eclipse IDE

---

## 📂 Project Structure

```text
JavaDevelopment-Task3-ATMInterface/
│
├── src/
│   └── Task3/
│       ├── Main.java
│       ├── LoginForm.java
│       ├── DashboardForm.java
│       ├── DepositForm.java
│       ├── WithdrawForm.java
│       ├── TransferForm.java
│       ├── TransactionHistoryForm.java
│       ├── BalanceEnquiryForm.java
│       ├── DBConnection.java
│       ├── User.java
│       ├── UserDAO.java
│       └── TransactionDAO.java
│
├── lib/
│   └── ojdbc11.jar
│
├── screenshots/
│
├── database.sql
├── README.md
├── .classpath
└── .project
```

---

## 🗄 Database

The application uses an **Oracle Database** connected through **JDBC**.

The `database.sql` file contains:
- Database table creation scripts
- Sample user records
- SQL statements required to run the project

---

## ▶️ How to Run

1. Clone this repository.
2. Open the project in Eclipse IDE.
3. Add the Oracle JDBC Driver (`ojdbc11.jar`) to the project's build path.
4. Execute the SQL statements in `database.sql` to create the required database tables.
5. Update the database connection details in `DBConnection.java`.
6. Run `Main.java`.
7. Log in using a valid Account Number and PIN.
8. Perform ATM transactions.

---

## 📸 Screenshots

Screenshots of the application are available in the `screenshots` folder.

Example screens include:
- Login Screen
- Dashboard
- Deposit
- Withdraw
- Transfer
- Balance Enquiry
- Transaction History

---

## 🎯 Learning Outcomes

This project helped strengthen my understanding of:

- Java Swing GUI Development
- Object-Oriented Programming (OOP)
- JDBC Connectivity
- Oracle Database Integration
- SQL CRUD Operations
- Event Handling
- Input Validation
- Desktop Application Development

---

## 👩‍💻 Author

**Anushka Sinha**

**Oasis Infobyte – Java Development Internship**

---

⭐ Thank you for visiting this project!