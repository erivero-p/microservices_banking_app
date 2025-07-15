# Customer Service

## Description
The Customer Service is responsible for managing user profiles in the Digital Banking Platform. It handles customer registration, profile updates, and basic validations (e.g., email format, required fields).

## Responsibilities
- Create and update customer records.
- Validate incoming customer data.
- Provide basic CRUD APIs for customer information.

## Endpoints
- `POST /customers` – Register a new customer
- `GET /customers/{id}` – Retrieve customer details
- `PUT /customers/{id}` – Update customer profile

## Step-by-Step Development Plan
1. Scaffold the controller and service classes.
2. Define the `Customer` domain model.
3. Implement validation logic and DTOs.
4. Write unit tests using JUnit 5 (start with test-first approach).
5. Add integration tests for REST endpoints.
6. Connect to PostgreSQL and persist customer data.
7. Add test coverage reports and validate with your team.
