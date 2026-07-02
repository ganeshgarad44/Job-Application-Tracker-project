# Job Application and Interview Tracker

Full-stack Spring Boot portfolio project for tracking job applications, interview rounds, and reminder emails.

## Tech Stack

Java 21, Spring Boot, Spring Security JWT, Spring Data JPA, PostgreSQL, Spring Scheduler, Spring Mail, Docker.

## Project Structure

- `controller` REST endpoints
- `service` business logic
- `repository` data access
- `entity` JPA models
- `dto` request/response payloads
- `config` application and security configuration
- `security` JWT services and filter
- `scheduler` reminder job
- `exception` global handling

## Local Setup

1. Copy `.env.example` to `.env` and adjust secrets if needed.
2. Start the full stack with Docker:

```bash
docker compose up --build
```

3. App runs on `http://localhost:8080`.
4. MailHog UI runs on `http://localhost:8025`.

## API Flow

1. Register a user with `POST /api/auth/register`.
2. Login with `POST /api/auth/login` and copy the JWT.
3. Send `Authorization: Bearer <token>` for secured endpoints.
4. Create applications, interview rounds, and reminders using the CRUD APIs.

## Example Requests

### Register

```json
{
  "email": "user@example.com",
  "password": "Password123!",
  "fullName": "Test User"
}
```

### Login

```json
{
  "email": "user@example.com",
  "password": "Password123!"
}
```

## Notes

- Database schema is defined in `src/main/resources/schema.sql`.
- Scheduled interview reminders run every 15 minutes and look ahead 24 hours.
- Spring Mail is configured to work with MailHog in Docker for local testing.