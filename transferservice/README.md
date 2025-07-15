# Transfer Service

## Description
Handles money transfers between accounts (Same bank). Ensures that transfers are atomic, consistent, and properly validated.

## Responsibilities
- Check source account balance before transferring.
- Debit source account and credit destination account.
- Generate and publish transfer events.

## Endpoints
- `POST /transfers` – Execute a transfer
- `GET /transfers/{id}` – Get transfer status

## Step-by-Step Development Plan
1. Define the `TransferRequest` DTO and controller.
2. Write business rules using TDD (e.g., insufficient balance).
3. Mock calls to Account Service for balance verification.
4. Create transactional flow and unit tests for failure cases.
5. Generate and publish a transfer completion event (JSON).
6. Write integration tests and monitor logs.
