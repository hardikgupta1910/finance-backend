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
* 🐘 PostgreSQL Database Integration
* ☁️ Ready for Cloud Deployment (Render)

---

# 🌐 Base URL

### 🔹 Local

```
http://localhost:8080
```

### 🔹 Production (Render)

```
https://<your-app-name>.onrender.com
```

---

# 🔐 Authentication Flow

### 1️⃣ Signup

```
POST /auth/signup
```

### 2️⃣ Signin

```
POST /auth/signin
```

Response:

```json
"JWT_TOKEN"
```

### 3️⃣ Use Token

All protected endpoints require:

```
Authorization: Bearer <JWT_TOKEN>
```

---

# 👥 Roles & Permissions

| Role    | Access                        |
| ------- | ----------------------------- |
| ADMIN   | Full access                   |
| ANALYST | Read + Create records         |
| VIEWER  | Read-only (summary & records) |

---

# 📁 Project Structure

```
src/main/java/com/finance/backend/

├── Config/
│   ├── SecurityConfig.java
│   ├── JwtFilter.java
│   ├── JwtService.java
│   ├── PasswordConfig.java
│   ├── OpenApiConfig.java
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
* Custom `JwtFilter` for request interception
* Roles mapped to Spring Security `GrantedAuthority`
* Method-level authorization using `@PreAuthorize`
* Passwords hashed using BCrypt
* Authenticated user extracted via `SecurityContext`

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
* `PATCH /users/{id}/role` → admin only
* `PATCH /users/{id}/status` → admin only
* `DELETE /users/{id}` → self or admin

---

## 💰 Financial Records

* `POST /records` → admin only
* `GET /records` → paginated + filtered + sorted
* `PUT /records/{id}` → admin only
* `DELETE /records/{id}` → admin only

---

## 🔍 Search (Keyword-Based)

```
GET /records/search?keyword=food&page=0&size=5
```

Search fields:

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

```
GET /records?page=0&size=5&type=INCOME
```

* `page` → page index (0-based)
* `size` → number of records
* Sorted by `date (descending)`

---

# 📘 Swagger API Documentation

Access Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

### 🔐 Authorize in Swagger

1. Click **Authorize 🔒**
2. Enter:

```
Bearer YOUR_JWT_TOKEN
```

3. Click **Authorize**

---

# ⚙️ Configuration

## 🔐 Environment Variables (Production)

```
SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:<port>/<db>
SPRING_DATASOURCE_USERNAME=<username>
SPRING_DATASOURCE_PASSWORD=<password>

SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
```

---

# 🧾 Sample Request

```
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

# ⚠️ Error Response Format

```json
{
  "message": "Invalid credentials",
  "status": 400,
  "timestamp": "2026-04-05T10:30:00"
}
```

---

# ▶️ Run Project (Local)

```
mvn spring-boot:run
```

---

# 🧪 Testing

* Tested using Postman and Swagger
* JWT authentication verified
* Role-based authorization enforced
* Pagination and search validated
* PostgreSQL integration verified

---

# 🔜 Future Improvements

* Advanced filtering (amount/date range)
* Refresh token implementation
* Unit & integration testing
* Rate limiting
* Soft delete support

---

# 👨‍💻 Author

**Hardik Gupta**
B.Tech CSE (AI & ML)
📧 [hardikgupta8109@gmail.com](mailto:hardikgupta8109@gmail.com)
