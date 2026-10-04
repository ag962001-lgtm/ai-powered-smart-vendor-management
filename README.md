# AI-Powered Smart Vendor Management Platform

A backend-driven AI-powered vendor and inventory management system built with Java and Spring Boot. The platform manages vendors, products, sales, inventory, alerts, purchase orders, and uses Google Gemini for demand forecasting and reorder recommendations.

## Features

- Vendor management
- Product and inventory management
- Sales management
- Automatic low-stock alerts
- AI-powered demand forecasting
- AI-based reorder recommendations
- Automatic purchase-order generation
- Purchase-order status lifecycle
- Dashboard and vendor-wise reporting
- JWT-based authentication and role-based authorization
- Input validation
- Global exception handling

## Technology Stack

- Java 21
- Spring Boot 4.1.1
- Spring Data JPA
- Hibernate
- MySQL
- Spring Security
- JWT
- Spring AI
- Google Gemini API
- Maven
- REST APIs
- Postman

## Architecture

The project follows a layered Spring Boot architecture:

- **Controller Layer** — Handles REST API requests and responses.
- **Service Layer** — Contains business logic and application workflows.
- **Repository Layer** — Handles database operations using Spring Data JPA.
- **Entity Layer** — Represents database tables and relationships.
- **DTO Layer** — Handles structured request and response data.
- **Security Layer** — Handles JWT authentication and authorization.
- **Exception Layer** — Provides centralized exception handling.

## Core Business Workflow

1. Vendors are registered and linked with products.
2. Products maintain current stock and minimum stock thresholds.
3. Sales reduce product inventory automatically.
4. Low-stock conditions generate alerts.
5. Historical sales data is used for demand forecasting.
6. Google Gemini provides AI-based reorder recommendations.
7. The system can automatically create purchase orders for required products.
8. Purchase orders move through `PENDING → CONFIRMED → SHIPPED → DELIVERED`.
9. When an order is delivered, the product stock is automatically increased.

## AI-Powered Demand Forecasting

The platform integrates **Google Gemini through Spring AI**.

The demand forecasting workflow uses:

- Historical sales data
- Current product stock
- Product information
- Calculated demand forecast
- Recommended reorder quantity
- Gemini-powered reorder recommendation

The system can also automatically create a purchase order based on the recommended reorder quantity.

## Authentication and Authorization

The application uses **Spring Security** and **JWT-based authentication**.

- Users can register through the authentication API.
- Passwords are securely stored using BCrypt hashing.
- Users receive a JWT token after successful login.
- Protected APIs require a valid Bearer token.
- Role-based authorization is implemented for restricted operations.
- `ADMIN` users have administrative permissions such as product creation.
- Invalid or missing authentication tokens are rejected by the security layer.

## Important API Endpoints

### Authentication

- `POST /auth/register` — Register a new user
- `POST /auth/login` — Login and receive JWT token

### Vendors

- `GET /vendors` — Get all vendors
- `POST /vendors` — Create a vendor
- `PUT /vendors/{id}` — Update a vendor
- `DELETE /vendors/{id}` — Delete a vendor

### Products

- `GET /products` — Get all products
- `POST /products` — Create a product
- `GET /products/low-stock` — Get low-stock products
- `PUT /products/{id}/increase-stock` — Increase stock
- `PUT /products/{id}/decrease-stock` — Decrease stock
- `DELETE /products/{id}` — Delete a product

### Sales

- `POST /sales/{productId}` — Record a sale
- `GET /sales` — Get sales records

### AI Forecasting

- `GET /forecast/{productId}` — Generate demand forecast and reorder recommendation

### Purchase Orders

- `GET /purchase-orders` — Get all purchase orders
- `GET /purchase-orders/vendor/{vendorId}` — Get vendor-wise purchase orders
- `POST /purchase-orders/auto-reorder/{productId}` — Create automatic reorder
- `PUT /purchase-orders/{id}/status` — Update purchase-order status

### Alerts

- `GET /alerts` — Get alerts
- `POST /alerts/{productId}` — Create a low-stock alert
- `PUT /alerts/{id}/resolve` — Resolve an alert

### Dashboard

- `GET /dashboard` — Get dashboard statistics

## Database

The application uses **MySQL** as its primary relational database.

Main entities include:

- User
- Vendor
- Product
- SalesRecord
- Alert
- PurchaseOrder

Relationships between entities are managed using **JPA/Hibernate**.

## Google Gemini Configuration

The project uses **Spring AI** with the **Google Gemini API** for AI-powered demand forecasting and reorder recommendations.

Add your Gemini API key to your local `application.properties`:

```properties
spring.ai.google.genai.api-key=YOUR_GEMINI_API_KEY
spring.ai.google.genai.chat.model=gemini-3.6-flash
```

**Do not add your actual API key to this README or commit it to GitHub.**

Use your real API key only in your local configuration.

## How to Run

### 1. Configure MySQL

Create a MySQL database named:

```sql
CREATE DATABASE vendor_management;
```

Update your local database credentials in `application.properties`.

### 2. Configure Gemini API

Add your Gemini API key to your local `application.properties`.

### 3. Run the application

Using Maven:

```bash
./mvnw spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

### 4. Test the APIs

Use **Postman** to test the REST APIs, authentication flow, AI forecasting, inventory operations, alerts, and purchase-order lifecycle.

## Testing

The application has been tested using Postman for:

- User registration
- User login
- JWT authentication
- Role-based authorization
- Vendor CRUD operations
- Product CRUD operations
- Stock increase and decrease
- Low-stock detection
- Automatic low-stock alerts
- Alert resolution
- Sales recording
- AI demand forecasting
- AI reorder recommendations
- Automatic purchase-order creation
- Purchase-order status updates
- Inventory update after order delivery
- Dashboard statistics
- Input validation
- Global exception handling

## Error Handling and Validation

The project includes centralized exception handling using `@RestControllerAdvice`.

Validation is implemented for user registration requests.

The application returns structured error responses for validation and runtime exceptions.

## Project Status

**Completed MVP**

The backend implementation is complete with:

- Vendor and product management
- Inventory and sales management
- Low-stock alerts
- AI demand forecasting
- AI reorder recommendations
- Automatic purchase-order generation
- Purchase-order lifecycle management
- JWT authentication and role-based authorization
- Input validation
- Global exception handling
- Dashboard APIs
- Postman testing

## Security Note

Never commit sensitive information such as:

- Gemini API keys
- Database passwords
- JWT secrets
- Other private credentials

Use local configuration or environment variables for sensitive values before publishing the project to GitHub.