# E-Commerce System

## 📌 Project Overview
This project is a backend E-Commerce system inspired by Amazon, developed using Spring Boot.  
It demonstrates core backend concepts such as RESTful APIs, business logic handling, role validation, and product management.

The system allows users to browse products, search, filter, purchase items, and manage balances, while merchants can track their earnings.  
Administrative actions are restricted to ensure correct system behavior.

---

## ⚙️ Technologies Used
- Java
- Spring Boot
- RESTful APIs
- Maven
- JPA / Hibernate
- MySQL (or any relational database)

---

## 🚀 Features

### 🛍️ Product Features
- View all products
- View only in-stock products
- Search for a product by name and view full information
- Sort products:
  - Cheap to expensive
  - Expensive to cheap
- Filter products by category name

---

### 👤 User Features
- Add balance to a user
- Buy products
- View user purchase history by ID

---

### 🏪 Merchant Features
- Calculate total earnings for a merchant

---

### 🔐 Business Rules
- Admin users are not allowed to buy products
- Role validation is checked during purchase operations
- Prevents invalid actions to maintain system integrity

---

## ➕ Extra Endpoints
- Sort products from cheap to expensive
- Sort products from expensive to cheap
- View products by category name
- View in-stock products only
- Search product by name
- Add balance to user
- Calculate merchant earnings

---

## 📂 Project Structure

src
└── main
├── java
│ ├── controller
│ ├── service
│ ├── repository
│ └── model
└── resources
└── application.properties


---

## 📈 Learning Outcomes
- Implemented real-world E-Commerce backend logic
- Applied role-based access validation
- Designed clean REST APIs using Spring Boot
- Practiced layered backend architecture

---

## Developed By
Asrar Fallatah 
