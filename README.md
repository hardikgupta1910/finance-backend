# 💰 Finance Backend API

A secure and scalable backend system built using **Spring Boot**, featuring **JWT authentication**, **BCrypt password hashing**, **role-based authorization**, **pagination**, **search**, and **Swagger API documentation with JWT support**.

---

# 🚀 Features

* 🔐 JWT Authentication (Signup / Signin)
* 🔒 BCrypt Password Encryption
* 👤 Role-Based Access Control (ADMIN, ANALYST, VIEWER)
* 💰 Financial Record Management
* 📊 Dashboard APIs (Summary, Category, Recent)
* 📄 Pagination & Sorting
* 🔍 Keyword Search (category & note)
* 🛡️ Method-level Security using `@PreAuthorize`
* ⚠️ Global Exception Handling
* 📘 Swagger API Documentation with JWT Authorization

---

# 🌐 Live API

Base URL:

https://finance-backend-1r92.onrender.com

⚠️ Notes:

* Root endpoint `/` is secured → returns **403 Forbidden**
* Use endpoints like `/auth/signup`, `/auth/signin`, `/records`

---

# 📘 Swagger UI

https://finance-backend-1r92.onrender.com/swagger-ui/index.html

Use Swagger to test APIs directly.

---

# 🔐 Authentication Flow

## 1️⃣ Signup

POST /auth/signup

```json
{
  "email": "user1@gmail.com",
  "password": "qwerty1234",
  "userName": "user1"
}
```

Default role: **VIEWER**

---

## 2️⃣ Signin

POST /auth/signin

```json
{
  "email": "admin@finance.com",
  "password": "admin123"
}
```

Response:

```json
"JWT_TOKEN"
```

---

## 3️⃣ Authorization

All protected endpoints require:

Authorization: Bearer <JWT_TOKEN>

---

# 🔑 Default Admin

Auto-created on application startup:

```json
{
  "email": "admin@finance.com",
  "password": "admin123"
}
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

src/main/java/com/finance/backend/

├── config/
│   ├── SecurityConfig.java
│   ├── JwtFilter.java
│   ├── JwtService.java
│   ├── PasswordConfig.java
│   ├── OpenApiConfig.java
│   ├── DataInitializer.java
│
├── controller/
├── dto/
├── model/
├── repository/
├── service/
├── serviceImpl/
├── exception/
│
└── DemoApplication.java

---

# 🌐 API Endpoints

## 🔐 Auth

* POST /auth/signup
* POST /auth/signin

---

## 👤 Users

* GET /users/{id} → self or admin
* GET /users → admin only
* PATCH /users/{id} → self or admin
* PATCH /users/{id}/role → admin
* PATCH /users/{id}/status → admin
* DELETE /users/{id} → self or admin

---

## 💰 Financial Records

* POST /records → admin only
* GET /records → paginated, filtered, sorted
* PUT /records/{id} → admin only
* DELETE /records/{id} → admin only

---

## 🔍 Search

GET /records/search?keyword=food&page=0&size=5

Search fields:

* category
* note

---

## 📊 Dashboard

* GET /records/summary
* GET /records/summary/category
* GET /records/recent

---

# 📄 Pagination Example

GET /records?page=0&size=5&type=INCOME

* page → 0-based index
* size → number of records
* Default sorting → date DESC

---

# ⚙️ Configuration (Render)

Environment variables:

SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:5432/<db>
SPRING_DATASOURCE_USERNAME=<username>
FINANCE_DB_PASSWORD=<password>

ADMIN_EMAIL=[admin@finance.com](mailto:admin@finance.com)
ADMIN_PASSWORD=admin123

---

# ⚠️ Error Response

```json
{
  "message": "Invalid credentials",
  "status": 400,
  "timestamp": "2026-04-05T10:30:00"
}
```

---

# ▶️ Run Locally

mvn spring-boot:run

---

# 🧪 Testing

* Tested using Postman & Swagger
* JWT authentication verified
* Role-based access enforced
* Pagination & search validated

---

# 🔜 Future Improvements

* Advanced filters (amount/date range)
* Refresh tokens
* Unit & integration tests
* Rate limiting
* Soft delete

---

# 👨‍💻 Author

Hardik Gupta
B.Tech CSE (AI & ML)
[hardikgupta8109@gmail.com](mailto:hardikgupta8109@gmail.com)
