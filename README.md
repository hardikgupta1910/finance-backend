# 💰 Finance Backend API

A secure and scalable backend system built using **Spring Boot**, featuring **JWT authentication**, **BCrypt password hashing**, **role-based authorization**, **pagination**, **keyword search**, and **Swagger API documentation with JWT support**.

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

⚠️ Note:

* Root endpoint `/` is secured → will return **403 Forbidden**
* Use specific endpoints like `/auth/signup`, `/auth/signin`, `/records`, etc.

---

# 📘 Swagger UI

https://finance-backend-1r92.onrender.com/swagger-ui/index.html

⚠️ Note:
This service is hosted on Render free tier.  
It may take 30–60 seconds to respond on first request due to cold start.

👉 Use Swagger to explore and test all APIs easily.

---

# 🔐 Authentication Flow

## 1️⃣ Signup

```http
POST /auth/signup
```

### Sample Request Body

```json
{
  "email": "user1@gmail.com",
  "password": "qwerty1234",
  "userName": "user1"
}
```

👉 Default role assigned: **VIEWER**

---

## 2️⃣ Signin

```http
POST /auth/signin
```

### Sample Request Body (Admin)

```json
{
  "email": "admin@finance.com",
  "password": "admin123"
}
```

### Response

```json
"JWT_TOKEN"
```

---

## 3️⃣ Use Token

All protected endpoints require:

```http
Authorization: Bearer <JWT_TOKEN>
```

---

# 🔑 Default Admin Credentials

> Admin user is automatically created on first application startup.

```json
{
  "email": "admin@finance.com",
  "password": "admin123"
}
```

💡 First-time users can directly login using default admin credentials.

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
```

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

* `POST /records` → admin only
* `GET /records` → paginated + filter + sorted
* `PUT /records/{id}` → admin only
* `DELETE /records/{id}` → admin only

---

## 🔍 Search

```http
GET /records/search?keyword=food&page=0&size=5
```

Searches:

* category
* note

---

## 📊 Dashboard

* `GET /records/summary`
* `GET /records/summary/category`
* `GET /records/recent`

---

# 📄 Pagination Example

```http
GET /records?page=0&size=5&type=INCOME
```

* `page` → page index (0-based)
* `size` → number of records
* Sorted by `date DESC`

---

# ⚙️ Configuration (Render Deployment)

Environment variables used:

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:5432/<db>
SPRING_DATASOURCE_USERNAME=<username>
SPRING_DATASOURCE_PASSWORD=<password>

ADMIN_EMAIL=admin@finance.com
ADMIN_PASSWORD=admin123
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

# ▶️ Run Locally

```bash
mvn spring-boot:run
```

---

# 🧪 Testing

* Tested using Postman and Swagger
* JWT authentication verified
* Role-based authorization enforced
* Pagination and search validated

---

# 🔜 Future Improvements

* Advanced filtering (amount/date range)
* Refresh token implementation
* Unit & integration testing
* Rate limiting
* Soft delete support

---

# 👨‍💻 Author

Hardik Gupta
B.Tech CSE (AI & ML)
hardikgupta8109@gmail.com
