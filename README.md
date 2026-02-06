# Credit Card Management System

## 📌 Project Overview
The **Credit Card Management System** is a Java-based backend web application developed using **Java Servlets, JDBC, and MySQL**.  
It simulates real-world credit card operations such as **user authentication, purchase processing, credit limit validation, billing generation, EMI conversion, and transaction monitoring**.

The project is built without using any frameworks like Spring, focusing on **core Java EE concepts** and **Servlet lifecycle understanding**.

---

## 🛠 Tech Stack
- **Java:** JDK 17.0.12  
- **Server:** Apache Tomcat 9.0.115  
- **Build Tool:** Maven 3.9.12  
- **Database:** MySQL 8.0.45  
- **API:** Javax Servlet  
- **IDE:** IntelliJ IDEA  

---



## ✨ Features
- User signup and login with session management  
- Credit card linking to user  
- Purchase transaction processing  
- Credit limit validation before purchase  
- Monthly billing and due amount calculation  
- EMI conversion for transactions  
- Transaction history and monitoring  
- Secure session-based access 
- User logout using session invalidation

---

## 📂 Project Structure Architecture
The application follows a **layered MVC architecture**:
---

```
Credit_Card_Management_System
│
├── pom.xml
├── src/main/java
│   └── com.ccms
│       ├── config
│       │   └── DBConnection.java
│       │
│       ├── model
│       │   ├── User.java
│       │   ├── CreditCard.java
│       │   ├── Transaction.java
│       │   └── Emi.java
│       │
│       ├── dao
│       │   ├── UserDAO.java
│       │   ├── CardDAO.java
│       │   ├── TransactionDAO.java
│       │   └── EmiDAO.java
│       │
│       ├── service
│       │   ├── UserService.java
│       │   ├── TransactionService.java
│       │   └── EmiService.java
│       │
│       └── servlet
│           ├── SignupServlet.java
│           ├── LoginServlet.java
│           ├── LogoutServlet.java
│           ├── PurchaseServlet.java
│           ├── BillingServlet.java
│           ├── EmiServlet.java
│           └── TransactionServlet.java
│
└── src/main/webapp
    └── WEB-INF
        └── web.xml
```

---
