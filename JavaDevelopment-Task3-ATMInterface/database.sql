-- =====================================================
-- ATM Interface Database Script
-- Oasis Infobyte Java Development Internship
-- Author: Anushka Sinha
-- Database: Oracle
-- =====================================================

-- =========================
-- USERS TABLE
-- =========================

CREATE TABLE USERS3 (
    USER_ID NUMBER PRIMARY KEY,
    USERNAME VARCHAR2(50) NOT NULL,
    PIN VARCHAR2(4) NOT NULL,
    ACCOUNT_NO VARCHAR2(20) NOT NULL,
    NAME VARCHAR2(100) NOT NULL,
    BALANCE NUMBER(12,2)
);

-- =========================
-- TRANSACTIONS TABLE
-- =========================

CREATE TABLE TRANSACTION3 (
    TRANSACTION_ID NUMBER PRIMARY KEY,
    ACCOUNT_NO VARCHAR2(20) NOT NULL,
    TRANSACTION_TYPE VARCHAR2(30) NOT NULL,
    AMOUNT NUMBER(12,2) NOT NULL,
    BALANCE NUMBER(12,2) NOT NULL,
    TRANSACTION_DATE TIMESTAMP DEFAULT SYSTIMESTAMP
);

-- =========================
-- SAMPLE USERS
-- =========================

INSERT INTO USERS3 VALUES (1,'anushka','1234','10001','Anushka Sinha',50000);
INSERT INTO USERS3 VALUES (2,'rahul','1111','10002','Rahul Sharma',30000);
INSERT INTO USERS3 VALUES (3,'priya','2222','10003','Priya Singh',44000);
INSERT INTO USERS3 VALUES (4,'amit','3333','10004','Amit Kumar',45000);
INSERT INTO USERS3 VALUES (5,'neha','4444','10005','Neha Verma',60000);
INSERT INTO USERS3 VALUES (6,'rohit','5555','10006','Rohit Gupta',35000);
INSERT INTO USERS3 VALUES (7,'kavya','6666','10007','Kavya Sharma',80000);
INSERT INTO USERS3 VALUES (8,'arjun','7777','10008','Arjun Mehta',90000);

COMMIT;