# Account Service

## Description
This microservice manages customer bank accounts. It supports operations such as account creation, balance checks, deposits, and withdrawals.

## Responsibilities
- Create accounts for registered customers.
- Manage account balances and transactions.
- Expose safe APIs for external services to retrieve account details.

## Endpoints
- `POST /accounts` – Create a new account
- `GET /accounts/{id}` – Get account details
- `PATCH /accounts/{id}/deposit` – Deposit funds
- `PATCH /accounts/{id}/withdraw` – Withdraw funds

## Step-by-Step Development Plan
1. Define the `Account` entity and controller.
2. Implement basic balance and validation logic.
3. Write unit tests for service logic and edge cases (e.g., negative deposits).
4. Set up integration tests using a real Postgres instance.
5. Make sure each endpoint is properly validated and covered.
