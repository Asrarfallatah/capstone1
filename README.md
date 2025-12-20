# E-Commerce Backend System

## 📌 Project Overview
This project is a **backend E-Commerce system** inspired by Amazon, developed using **Spring Boot**.  
It focuses on implementing real-world online shopping logic such as product browsing, purchasing, balance management, merchant earnings, and role-based validations.

The system is designed to simulate a complete backend workflow for an E-Commerce platform where users can purchase products, merchants can track their earnings, and admins are restricted from performing customer actions.

---

## 🎯 System Idea
The idea behind this system is to build a structured E-Commerce backend that:
- Allows users to browse and search products easily
- Ensures only available (in-stock) products can be purchased
- Supports filtering and sorting to improve user experience
- Tracks merchant earnings accurately
- Enforces strict business rules to prevent invalid actions

---

## ⚙️ Technologies Used
- Java  
- Spring Boot  
- RESTful APIs  
- Maven  
- JPA / Hibernate  
- MySQL (or any relational database)  
- Postman (API testing)

---

## 🛍️ Product Features
- View all products
- View only **in-stock products**
- Search for a product by name and view full details
- Filter products by **category name**
- Sort products:
  - Cheap → Expensive
  - Expensive → Cheap

---

## 👤 User Features
- Add balance to a user account
- Purchase products
- View user purchase history by user ID

---

## 🏪 Merchant Features
- Calculate total earnings for a merchant
- Track sales through completed purchases

---

## ➕ Extra Endpoints
- Sort products from cheap to expensive
- Sort products from expensive to cheap
- View products by category name
- View only in-stock products
- Search product by name
- Add balance to user
- Calculate merchant earnings
- Get user purchase history by ID

---

## 🔐 Business Rules & Validations
- Admin users **cannot buy products**
- Purchase method checks user role before execution
- Products must be in stock to be purchased
- User must have sufficient balance
- All critical logic is enforced at the service layer

---

## 🧠 System Architecture
The project follows a **layered architecture**:
- **Controller Layer** – Handles HTTP requests
- **Service Layer** – Contains business logic and validations
- **Repository Layer** – Manages database operations
- **Model Layer** – Represents database entities

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

## 🧪 API Testing
All endpoints were tested using **Postman** to ensure:
- Correct responses
- Valid error handling
- Business rule enforcement
- Stable purchase workflows

---

## 📈 Learning Outcomes
- Designing a real-world E-Commerce backend
- Applying role-based restrictions
- Implementing complex business logic
- Structuring scalable Spring Boot applications
- Writing clean and maintainable code

---

## 📝 Final Notes
This project was developed as part of a **Backend Capstone Project** and demonstrates practical backend development skills aligned with industry standards.

---

## ⭐ Thank You
Thank you for reviewing this project!

------------------
## Developed By
Asrar Fallatah 
