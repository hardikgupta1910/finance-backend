# 💰 Finance Backend (Spring Boot + JWT)

A secure backend system for managing users and financial records with **JWT-based authentication** and **role-based access control**.

---

# 🚀 Features

* 🔐 JWT Authentication (Signin / Signup)
* 👤 User Management (Role + Status control)
* 💰 Financial Records (Income & Expense tracking)
* 📊 Dashboard Analytics (Summary, Category, Recent)
* 🛡️ Secure APIs (No fake userId, fully token-based)

---

# 🧠 Authentication Flow

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

### 3️⃣ Access Protected APIs

```http
Authorization: Bearer <JWT_TOKEN>
```

---

# 📁 Project Structure

```
src/main/java/com/finance/backend/

├── Config/
│   ├── SecurityConfig.java
│   ├── JwtFilter.java
│   ├── JwtService.java
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
├── Domain/
├── Model/
├── Repository/
├── Service/
├── ServiceImpl/
├── Exception/
│
└── DemoApplication.java
```

---

# 🔐 Security Implementation

* Custom `JwtFilter` (OncePerRequestFilter)
* Token validation on every request
* Stateless session (no server-side storage)
* User identity extracted from token
* Removed insecure `userId` from request params

---

# 🌐 Base URL

```http
http://localhost:8080
```

All API endpoints are relative to this base URL.

---

### Example

```http
POST http://localhost:8080/auth/signin
GET http://localhost:8080/records
```


# 🌐 API Endpoints


## 🔐 Auth APIs

* `POST /auth/signup`
* `POST /auth/signin`

---

## 👤 User APIs

* `GET /users/{id}`
* `GET /users`
* `PATCH /users/{id}/role`
* `PATCH /users/{id}/status`
* `PATCH /users/{id}`
* `DELETE /users/{id}`

---

## 💰 Financial APIs

* `POST /records`
* `GET /records`
* `PUT /records/{id}`
* `DELETE /records/{id}`

---

## 📊 Dashboard APIs

* `GET /records/summary`
* `GET /records/summary/category`
* `GET /records/recent`

---

# 🧾 Sample Request

### Create Financial Record

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

# 🛠️ Tech Stack

* Java
* Spring Boot
* Spring Security
* JWT (jjwt)
* Spring Data JPA
* Hibernate
* MySQL

---

# ⚙️ Configuration (Environment Variables)

Sensitive data is stored using environment variables:

```properties
spring.datasource.password=${FINANCE_DB_PASSWORD}
```

---

# ▶️ Run Project

```bash
mvn spring-boot:run
```

---

# 🧪 Testing

* Tested using Postman
* JWT authentication verified
* Unauthorized access blocked

---

# ⚠️ Limitations

* Password stored in plain text (no hashing yet)
* No refresh token system
* No advanced role annotations (`@PreAuthorize`)
* Basic exception handling

---

# 🔜 Future Improvements

* BCrypt password hashing
* Role-based authorization (Spring Security annotations)
* Refresh tokens
* Logging & monitoring
* Unit & integration tests

---

# 👨‍💻 Author

Hardik Gupta
B.Tech CSE (AI & ML)

---
