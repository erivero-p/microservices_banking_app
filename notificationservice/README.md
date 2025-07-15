# Notification Service

## Description
This service listens for completed transfer events and sends user notifications. Simulates email or push delivery.

## Responsibilities
- Subscribe to transfer events.
- Send confirmation messages or alerts.
- Allow manual triggering of notifications for testing.

## Endpoints
- `POST /notifications/manual` – Trigger a test notification (for dev/testing)

## Step-by-Step Development Plan
1. Set up an event listener (HTTP or Kafka-based).
2. Create a handler for `transfer.completed` events.
3. Write tests for deserialization and notification logic.
4. Log all outgoing messages for auditing.
5. Simulate different delivery channels using logging or a mock email service.
