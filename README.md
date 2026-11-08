# RateGuard

A distributed rate-limiting engine for APIs, built with Java 17 and Spring Boot 3.

**Status: early development.** The Spring Boot service skeleton and its first REST endpoints are in place. The rate-limiting engine itself is the next stage and is not built yet. See the roadmap below.

## The problem

A naive rate limiter reads a request counter and then updates it in two separate steps. When many requests arrive at once, several can read the same value before any of them writes it back, and all of them slip past the limit. This is a check-then-act race condition, and it gets worse when the limiter runs on more than one server.

## Planned design

- A request-interception layer that checks every incoming API request against a per-client limit.
- Token Bucket algorithm: each client has a bucket that refills at a steady rate, and every request spends one token.
- The check-and-spend step is designed to run as a single atomic Redis Lua script, so concurrent requests cannot interleave.
- Standard RFC 7807 Problem Details responses when a client is rate limited.
- PostgreSQL for client configuration and tiers.

## What works today

- Spring Boot 3.3.4 project on Java 17 (Maven).
- REST endpoints under `/api/v1`:

| Method | Path | Purpose |
|--------|------|---------|
| GET | `/api/v1/health` | Service health check |
| GET | `/api/v1/echo/{name}` | Echo endpoint for testing request handling |
| GET | `/api/v1/greet/{name}?greeting=Hi` | Echo with a query parameter |
| POST | `/api/v1/clients` | Accepts a client registration (name, tier, rate limit) and returns a confirmation. Not stored yet. |

Example:

```bash
curl http://localhost:8080/api/v1/health

curl -X POST http://localhost:8080/api/v1/clients \
  -H "Content-Type: application/json" \
  -d '{"clientName":"demo","tier":"free","rateLimit":100}'
```

## Roadmap

- [x] Spring Boot 3 service skeleton and REST endpoints
- [x] Client registration request model
- [ ] Custom request filter that intercepts every API call
- [ ] RFC 7807 Problem Details error handling
- [ ] Token Bucket rate limiting
- [ ] Atomic Redis Lua script for the check-and-spend step
- [ ] PostgreSQL storage for registered clients
- [ ] Concurrency tests showing no request exceeds the limit under load

## Tech stack

Java 17, Spring Boot 3.3.4, Maven. Planned: Redis, PostgreSQL.

## Running locally

Requires JDK 17 and Maven.

```bash
mvn spring-boot:run
```

The service starts on `http://localhost:8080`.

## Author

Manvi Ranjan, B.Tech Computer Science (Cybersecurity), UPES
