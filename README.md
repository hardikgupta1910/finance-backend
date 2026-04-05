# 💰 Finance Backend API

A secure backend system built using **Spring Boot**, featuring **JWT authentication**, **BCrypt password hashing**, **role-based authorization**, **pagination**, **keyword search**, and **Swagger API documentation**.

---

# 🚀 Features

* 🔐 JWT Authentication (Signin / Signup)
* 🔒 BCrypt Password Encryption
* 👤 User Management with Role-Based Access Control
* 💰 Financial Record Management
* 📊 Dashboard APIs (Summary, Category, Recent)
* 📄 Pagination & Sorting
* 🔍 Keyword Search (category & note)
* 🛡️ Method-level Security using `@PreAuthorize`
* ⚠️ Global Exception Handling
* 📘 Swagger API Documentation

---

# 🌐 Base URL

```http
http://localhost:8080
```

---

# 🔐 Authentication Flow

### 1️⃣ Signup

```http
POST /auth/signup
```

---

### 2️⃣ Signin

```http
POST /auth/signin
```

Response:

```json
"JWT_TOKEN"
```

---

### 3️⃣ Use Token

```http
Authorization: Bearer <JWT_TOKEN>
```

---

# 👥 Roles & Permissions

| Role    | Access        |
| ------- | ------------- |
| ADMIN   | Full access   |
| ANALYST | Read + Create |
| VIEWER  | Read-only     |

---

# 📁 Project Structure

```
src/main/java/com/finance/backend/

├── Config/
│   ├── SecurityConfig.java
│   ├── JwtFilter.java
│   ├── JwtService.java
│   ├── PasswordConfig.java
│
├── Controller/
│   ├── AuthController.java
│   ├── UserController.java
│   ├── FinancialRecordController.java
│
├── DTO/
├── Model/
├── Repository/
├── Service/
├── ServiceImpl/
├── Exception/
│   ├── GlobalExceptionHandler.java
│   ├── ErrorResponseDTO.java
│
└── DemoApplication.java
```

---

# 🔐 Security Architecture

* Stateless authentication using JWT
* Custom `JwtFilter` for request validation
* Roles mapped to `GrantedAuthority`
* Authorization handled via `@PreAuthorize`
* Passwords securely stored using BCrypt

---

# 🌐 API Endpoints

## 🔐 Auth

* `POST /auth/signup`
* `POST /auth/signin`

---

## 👤 Users

* `GET /users/{id}` → self or admin
* `GET /users` → admin only
* `PATCH /users/{id}` → self or admin
* `PATCH /users/{id}/role` → admin
* `PATCH /users/{id}/status` → admin
* `DELETE /users/{id}` → self or admin

---

## 💰 Financial Records

* `POST /records` → admin
* `GET /records` → paginated + filter + sorted
* `PUT /records/{id}` → admin
* `DELETE /records/{id}` → admin

---

## 🔍 Search (Keyword-Based)

```http
GET /records/search?keyword=food&page=0&size=5
```

Searches across:

* category
* note

---

## 📊 Dashboard

* `GET /records/summary`
* `GET /records/summary/category`
* `GET /records/recent`

---

# 📄 Pagination & Sorting

Example:

```http
GET /records?page=0&size=5
```

Sorted by:

```
date (descending)
```

---

# 🧾 Sample Request

```http
POST /records
```

```json
{
  "amount": 5000,
  "type": "INCOME",
  "category": "SALARY",
  "date": "2026-04-04T10:00:00",
  "note": "Monthly salary"
}
```

---

# 📘 API Documentation (Swagger)

Access Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

Features:

* Interactive API testing
* Request/response schema
* Try APIs directly

---

# ⚙️ Configuration

### Environment Variable

```properties
spring.datasource.password=${FINANCE_DB_PASSWORD}
```

---

# ⚠️ Error Response Format

```json
{
  "message": "Invalid credentials",
  "status": 400,
  "timestamp": "2026-04-05T10:30:00"
}
```

---

# ▶️ Run Project

```bash
mvn spring-boot:run
```

---

# 🧪 Testing

* Tested using Postman
* JWT authentication validated
* Pagination & search verified
* Role-based access enforced

---

# 🔜 Future Improvements

* Advanced filtering (amount/date range)
* Soft delete support
* Unit & integration tests
* Rate limiting

---

# 👨‍💻 Author

Hardik Gupta
B.Tech CSE (AI & ML)

---
