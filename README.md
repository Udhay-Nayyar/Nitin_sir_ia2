# 🍔 Online Food Ordering & Delivery Management System

A microservices-based **Online Food Ordering & Delivery Management System** built using **Spring Boot and Spring Cloud**.

The system is divided into independent services for users, food management, orders, payments, notifications, and API routing. It also demonstrates centralized configuration, JWT authentication, role-based authorization, OpenFeign communication, Redis caching, and database separation.

---

## 🏗️ Architecture

```text
                         ┌──────────────────────┐
                         │     API GATEWAY      │
                         │       :8080          │
                         └──────────┬───────────┘
                                    │
             ┌──────────────────────┼──────────────────────┐
             │                      │                      │
             ▼                      ▼                      ▼
   ┌─────────────────┐   ┌─────────────────┐   ┌─────────────────┐
   │  USER SERVICE   │   │  FOOD SERVICE   │   │  ORDER SERVICE  │
   │     :8081       │   │     :8082       │   │     :8083       │
   └─────────────────┘   └─────────────────┘   └────────┬────────┘
                                                        │
                                             ┌──────────┴──────────┐
                                             │                     │
                                             ▼                     ▼
                                  ┌─────────────────┐   ┌────────────────────┐
                                  │ PAYMENT SERVICE │   │ NOTIFICATION       │
                                  │     :8084       │   │ SERVICE :8085      │
                                  └─────────────────┘   └────────────────────┘

                    ┌──────────────────────────────┐
                    │       CONFIG SERVER         │
                    │           :8888             │
                    └──────────────────────────────┘

                    ┌──────────────────────────────┐
                    │            MYSQL             │
                    │           :3306              │
                    └──────────────────────────────┘

                    ┌──────────────────────────────┐
                    │            REDIS             │
                    │           :6379              │
                    └──────────────────────────────┘
```

---

# 🚀 Services

| Service | Port | Responsibility |
|---|---:|---|
| Config Server | `8888` | Centralized application configuration |
| API Gateway | `8080` | Single entry point and request routing |
| User Service | `8081` | User registration, login and JWT authentication |
| Food Service | `8082` | Food CRUD operations and Redis caching |
| Order Service | `8083` | Order creation and management |
| Payment Service | `8084` | Payment processing |
| Notification Service | `8085` | Notification simulation through console |

---

# 🛠️ Technology Stack

- Java 17
- Spring Boot 4
- Spring Cloud
- Spring Cloud Config
- Spring Cloud Gateway
- Spring Security
- JWT
- Spring Data JPA
- MySQL
- Redis
- OpenFeign
- Maven
- Postman
- Eclipse / VS Code

---

# 📁 Project Structure

```text
Nitin_sir_ia2/
│
├── api-gateway/
├── config-server/
├── user-service/
├── food-service/
├── order-service/
├── payment-service/
├── notification-service/
├── .gitignore
└── README.md
```

Each service is an independent Spring Boot project.

---

# ⚙️ Prerequisites

Install:

- Java 17 or higher
- Maven
- MySQL
- Redis
- Postman
- Eclipse or any Java IDE

Check Java:

```bash
java -version
```

Check Maven:

```bash
mvn -version
```

---

# 🗄️ Database Setup

Create the service databases in MySQL:

```sql
CREATE DATABASE userdb;
CREATE DATABASE fooddb;
CREATE DATABASE orderdb;
CREATE DATABASE paymentdb;
```

Database ownership:

```text
User Service     → userdb
Food Service     → fooddb
Order Service    → orderdb
Payment Service  → paymentdb
```

Services should not directly access another service's database.

---

# 🔐 Configuration

Configuration is centralized using **Spring Cloud Config Server**.

The Config Server runs on:

```text
http://localhost:8888
```

Configuration files are stored inside:

```text
config-server/
└── src/
    └── main/
        └── resources/
            └── config/
                ├── user-service.properties
                ├── food-service.properties
                ├── order-service.properties
                ├── payment-service.properties
                ├── notification-service.properties
                └── api-gateway.properties
```

Update your local MySQL password in the configuration files before starting the application.

**Do not commit real passwords, API keys, or private secrets to GitHub.**

---

# ▶️ Running the Application

Start the services in this order.

## 1. Config Server

```text
config-server/
```

```bash
mvn spring-boot:run
```

Port: `8888`

## 2. User Service

```text
user-service/
```

```bash
mvn spring-boot:run
```

Port: `8081`

## 3. Food Service

```text
food-service/
```

```bash
mvn spring-boot:run
```

Port: `8082`

## 4. Payment Service

```text
payment-service/
```

```bash
mvn spring-boot:run
```

Port: `8084`

## 5. Order Service

```text
order-service/
```

```bash
mvn spring-boot:run
```

Port: `8083`

## 6. Notification Service

```text
notification-service/
```

```bash
mvn spring-boot:run
```

Port: `8085`

## 7. API Gateway

```text
api-gateway/
```

```bash
mvn spring-boot:run
```

Port: `8080`

All client requests can then be sent through:

```text
http://localhost:8080
```

---

# 🌐 API Gateway

The API Gateway provides a single entry point for the application.

Routes:

```text
/users/**          → User Service :8081
/foods/**          → Food Service :8082
/orders/**         → Order Service :8083
/payments/**       → Payment Service :8084
/notifications/**  → Notification Service :8085
```

---

# 👤 User Service

The User Service handles:

- User registration
- User login
- User retrieval
- User update
- User deletion
- JWT authentication
- Role management

Supported roles:

```text
CUSTOMER
ADMIN
```

## Register Customer

```http
POST http://localhost:8080/users/register
```

```json
{
  "name": "Rahul Sharma",
  "email": "rahul@gmail.com",
  "password": "rahul123",
  "phone": "9876543211",
  "role": "CUSTOMER"
}
```

## Register Admin

```http
POST http://localhost:8080/users/register
```

```json
{
  "name": "Admin User",
  "email": "admin@fooddelivery.com",
  "password": "Admin@123",
  "phone": "9876543210",
  "role": "ADMIN"
}
```

---

# 🔑 Login

```http
POST http://localhost:8080/users/login
```

```json
{
  "email": "rahul@gmail.com",
  "password": "rahul123"
}
```

Copy the JWT returned by the login API.

For protected APIs:

```text
Authorization: Bearer <JWT_TOKEN>
```

---

# 🍕 Food Service

Operations:

- Add food
- Get food
- Get all food
- Update food
- Delete food

## Get All Food

```http
GET http://localhost:8080/foods
```

Requires `CUSTOMER` or `ADMIN`.

## Add Food

```http
POST http://localhost:8080/foods
```

Requires `ADMIN`.

```json
{
  "foodName": "Chicken Biryani",
  "price": 250,
  "category": "Main Course",
  "availability": true,
  "restaurantId": 1
}
```

## Update Food

```http
PUT http://localhost:8080/foods/{id}
```

Requires `ADMIN`.

## Delete Food

```http
DELETE http://localhost:8080/foods/{id}
```

Requires `ADMIN`.

---

# ⚡ Redis Caching

Food details are cached using Redis.

Redis runs on:

```text
localhost:6379
```

Caching flow:

```text
First Request
     ↓
Redis MISS
     ↓
MySQL
     ↓
Store Result in Redis
```

Next request:

```text
Second Request
     ↓
Redis HIT
     ↓
Return Cached Data
```

When food is updated or deleted, the corresponding cache entry is updated or evicted.

---

# 🛒 Order Service

The Order Service handles:

- Creating orders
- Getting an order
- Getting orders by user
- Cancelling orders

## Create Order

```http
POST http://localhost:8080/orders
```

Headers:

```text
Authorization: Bearer <CUSTOMER_JWT>
Content-Type: application/json
```

Example:

```json
{
  "userId": 3,
  "foodId": 3,
  "quantity": 2
}
```

The Order Service:

1. Calls Food Service using OpenFeign
2. Checks food availability
3. Calculates the total price
4. Saves the order
5. Calls Payment Service using OpenFeign
6. Updates the order status
7. Prints a notification to the console

Example:

```text
Food Price = ₹180
Quantity   = 2
Total      = ₹360
```

Expected status:

```text
CONFIRMED
```

## Get Order

```http
GET http://localhost:8080/orders/{id}
```

## Get User Orders

```http
GET http://localhost:8080/orders/user/{userId}
```

## Cancel Order

```http
PUT http://localhost:8080/orders/{id}/cancel
```

---

# 💳 Payment Service

```http
POST http://localhost:8080/payments
```

Example:

```json
{
  "orderId": 1,
  "amount": 360
}
```

Positive amount:

```text
SUCCESS
```

Invalid/non-positive amount:

```text
FAILED
```

---

# 🔗 OpenFeign Communication

The Order Service communicates with other services using OpenFeign.

```text
ORDER SERVICE
      |
      +---- OpenFeign ----> FOOD SERVICE
      |
      +---- OpenFeign ----> PAYMENT SERVICE
```

The Order Service does not directly access the Food or Payment databases.

---

# 🔔 Notification Service

The Notification Service currently simulates notifications through the console.

Example:

```text
=================================
NOTIFICATION SERVICE
Order 1 created successfully for User 3
=================================
```

The notification is printed after a successful order/payment flow.

---

# 📡 Kafka Note

The intended event-driven architecture is:

```text
Order Service
     |
     | order-created
     ↓
Kafka Topic
     |
     ↓
Notification Service
```

For the current local demonstration, Kafka is **not running** and the notification flow is simulated through console output.

---

# 🔐 Role-Based Authorization

## CUSTOMER

```text
✓ View food
✓ Create orders
✓ View orders
✓ Cancel orders
✗ Add food
✗ Update food
✗ Delete food
```

## ADMIN

```text
✓ View food
✓ Add food
✓ Update food
✓ Delete food
```

---

# 🧪 Complete Testing Flow

### Step 1 — Register

```http
POST http://localhost:8080/users/register
```

### Step 2 — Login

```http
POST http://localhost:8080/users/login
```

Copy the JWT.

### Step 3 — Get Food

```http
GET http://localhost:8080/foods
```

Use:

```text
Authorization: Bearer <JWT>
```

### Step 4 — Create Order

```http
POST http://localhost:8080/orders
```

```json
{
  "userId": 3,
  "foodId": 3,
  "quantity": 2
}
```

### Step 5 — Verify Payment

Expected:

```text
SUCCESS
```

### Step 6 — Verify Order

Expected:

```text
CONFIRMED
```

### Step 7 — Check Order Service Console

```text
ORDER ID = 1
TOTAL = 360.0
NOTIFICATION → Order 1 created successfully for User 3
```

---

# 🔄 Complete Flow

```text
Customer
   |
   v
API Gateway :8080
   |
   v
Order Service :8083
   |
   +---- Feign ----> Food Service :8082
   |
   v
Calculate Total
   |
   v
Order DB
   |
   +---- Feign ----> Payment Service :8084
   |
   v
Payment SUCCESS
   |
   v
Order CONFIRMED
   |
   v
Notification Console
```

---

# 📊 Database Ownership

```text
USER SERVICE
     ↓
   userdb

FOOD SERVICE
     ↓
   fooddb

ORDER SERVICE
     ↓
   orderdb

PAYMENT SERVICE
     ↓
  paymentdb
```

Each major service owns its own database.

---

# 📌 Important Notes

- The project is designed for local execution.
- Eureka/Service Discovery is not used.
- Services communicate using configured localhost URLs.
- Each major service owns its own database.
- JWT is used for authentication.
- Role-based authorization is implemented.
- Redis is used for Food Service caching.
- OpenFeign is used for inter-service communication.
- API Gateway provides a single entry point.
- Notification is currently simulated through console output.
- Kafka is documented as the intended event-driven notification mechanism but is not required for the current local demonstration.
- Do not commit production credentials or private secrets.

---

## 👨‍💻 Project

**Online Food Ordering & Delivery Management System**

Built with:

```text
Java 17
Spring Boot
Spring Cloud
Spring Security
JWT
OpenFeign
MySQL
Redis
Maven
```
