# 💰 Finance Backend API (Spring Boot + JWT + BCrypt)

A secure backend system for managing users and financial records with **JWT authentication**, **BCrypt password hashing**, and **role-based authorization**.

---

# 🚀 Features

* 🔐 JWT Authentication (Signin / Signup)
* 🔒 BCrypt Password Encryption
* 👤 User Management (Role & Status control)
* 💰 Financial Record Management
* 📊 Dashboard Analytics
* 🛡️ Role-Based Access Control (`@PreAuthorize`)
* ⚠️ Global Exception Handling

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

| Role    | Permissions                        |
| ------- | ---------------------------------- |
| ADMIN   | Full access (CRUD users & records) |
| ANALYST | Read + create/update records       |
| VIEWER  | Read-only access                   |

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
│   ├── SignupDTO.java
│   ├── SigninDTO.java
│   ├── AuthResponse.java
│   ├── UserRequestDTO.java
│   ├── UserResponseDTO.java
│   ├── FinancialRecordDTO.java
│   ├── FinancialRecordRequestDTO.java
│
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

* JWT-based stateless authentication
* Custom `JwtFilter` using `OncePerRequestFilter`
* Role-based authorization via `@PreAuthorize`
* Passwords securely stored using BCrypt
* Authentication stored in `SecurityContextHolder`

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
* `GET /records` → admin, analyst
* `PUT /records/{id}` → admin
* `DELETE /records/{id}` → admin

---

## 📊 Dashboard

* `GET /records/summary` → admin, analyst
* `GET /records/summary/category` → admin, analyst
* `GET /records/recent` → admin, analyst

---

# 🧾 Sample Request

### Create Record

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

Header:

```http
Authorization: Bearer <JWT_TOKEN>
```

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
* Role-based access verified
* Error handling standardized

---

# 🔜 Future Improvements

* Refresh token implementation
* Role hierarchy (ADMIN > ANALYST > VIEWER)
* Pagination & sorting
* Logging & monitoring
* Unit & integration tests

---

# 👨‍💻 Author

Hardik Gupta
B.Tech CSE (AI & ML)

---
