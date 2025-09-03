# 🛒 E-Commerce Platform

A full-stack E-Commerce application built with **Angular 16** (frontend) and **Spring Boot** (backend).  
The system is designed with **role-based access control** using **Spring Security** and **JWT authentication**.

---

## 🚀 Features

- **Multi-role Authentication & Authorization**
  - **Customer** – Browse products, place orders, track deliveries.
  - **Seller** – Manage product listings, inventory, and pricing.
  - **Employee** – Assist with operations, order processing, and support.
  - **Admin** – Full system access, manage users, monitor platform activities.

- **Secure Backend**
  - Spring Security + JWT for role-based access.
  - RESTful APIs following best practices.

- **Modern Frontend**
  - Angular 16 with a clean UI.
  - Responsive design for desktop and mobile.

- **Scalable Architecture**
  - Separation of concerns (frontend & backend decoupled).
  - Easily deployable in cloud environments.

---

## 🛠️ Tech Stack

**Frontend**
- Angular 16
- TypeScript
- HTML5, CSS3, Bootstrap / Material UI

**Backend**
- Spring Boot
- Spring Security (JWT)
- JPA / Hibernate
- PostgreSQL (or other RDBMS)

---

## 📂 Project Structure

E-Commerce/
├── backend/ # Spring Boot Application
│ ├── src/main/java/... # Java source code
│ ├── src/main/resources/ # Configurations
│ └── pom.xml # Maven build file
│
|
|
└── frontend/ # Angular Application
├── src/ # Angular components, services, etc.
├── angular.json # Angular configuration
└── package.json # Node dependencies
