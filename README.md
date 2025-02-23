# Spring Boot E-commerce Project

## Table of Contents
- [Project Overview](#project-overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Project Structure](#project-structure)
- [Future Enhancements](#future-enhancements)
- [Contributing](https://github.com/Rkarama26/R-tech-frontend)

---

## Project Overview
The **Spring E-commerce Project** is a full-stack application that provides a robust platform for buying and selling products online. It allows users to browse products, manage carts, place orders, and perform authentication securely. The backend is built using **Spring Boot** with features like JWT-based authentication and database management through **Spring Data JPA**.

---

## Features
- **User Authentication:**
  - JWT-based login and sign-up.
  - Secure password management.
- **Product Management:**
  - CRUD operations for products.
  - Filtering and sorting by product type and price.
- **Cart Management:**
  - Add, update, or remove items from the cart.
- **Order Management:**
  - Checkout and track orders.
- **Admin Features:**
  - Product inventory management.
  - User and order management.
- **Responsive Design:** 
  - Built for a seamless user experience on both desktop and mobile platforms.
- **- **payment gateway integration.**
  - Razorpay payment gateway 


---

## Technologies Used
### Backend:
- **Spring Boot**
- **Spring Security with JWT**
- **Spring Data JPA**
- **Hibernate**
- **MySQL**

### Tools:
- **Git for version control**
- **Postman for API testing**
- **Maven for project dependency management**
- **Swagger for documentation**

---

## Getting Started

### Prerequisites
- **Java 17** or later
- **MySQL** installed and running
- An IDE like IntelliJ IDEA or Eclipse for backend development

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/Rkarama26/R_tech/ 
   ```
2. Navigate to the Project Directory
   ```bash
   cd spring-ecommerce-project
    ```
3.Update the **application.properties** file with your MySql credentials
```bash
   spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
   spring.datasource.username=root
   spring.datasource.password=password
```
4.Build and run the backend
```bash
mvn clean install
mvn spring-boot:run
```

## API Documentation
The API documentation is available via Swagger UI:

- **URL**: `http://localhost:8080/swagger-ui/index.html#/`

Here are the main API endpoints for the project:

- **Authentication**
  - `POST /api/auth/signin` - User login.
  - `POST /api/auth/signup` - User registration.

- **Products**
  - `GET /api/products` - Retrieve all products.
  - `GET /api/products/id/{id}` Retrieve a Product.

- **Cart**
   - `GET /api/cart/add` Add item to cart.
   - `GET /api/cart/`  Get cart.

- **Cart Item**
  - `PUT /api/cart_item/{id}` - Update cart items.
  - `DELETE /api/cart_item/{id}` - Remove items from the cart.

- **Orders**
  - `POST /api/orders` - Place an order.
  - `GET /api/orders` - Track all orders for the user.

 
## Future Enhancements
- **Add email notifications for orders.**
- **Enhance product recommendation using AI/ML.**
- **Add multi-language support.**



