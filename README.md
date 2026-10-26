<h1 align="center">🎬 CineStage <span align="right">— Backend</span></h1>

<p align="center">
  A full-featured movie ticket booking system backend — built with Spring Boot, PostgreSQL, and a live TMDB integration for real movie posters.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-007396?style=flat&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=flat&logo=springsecurity&logoColor=white" />
  <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=flat&logo=postgresql&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=flat&logo=spring&logoColor=white" />
  <img src="https://img.shields.io/badge/TMDB%20API-01D277?style=flat&logo=themoviedatabase&logoColor=white" />
  <img src="https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white" />
  <img src="https://img.shields.io/badge/Render-46E3B7?style=flat&logo=render&logoColor=black" />
  <img src="https://img.shields.io/badge/Neon-00E599?style=flat&logo=neon&logoColor=black" />
</p>

---

## 📖 About

**CineStage** is a cinema ticket booking platform — the kind of system that powers a real multiplex chain's ticket sales, built end-to-end. This repository is the **backend**: a Spring Boot REST API handling everything from theatre/screen/seat management to show scheduling, bookings, and live poster fetching from TMDB.

The frontend that consumes this API is **CineStage** (built separately, generated via AI frontend tooling and iterated on in VS Code).

---

## 🌐 Live Demo

- **Live app:** [https://cinestage-soumadeep1221.netlify.app](https://cinestage-soumadeep1221.netlify.app)
- **Backend (Render):** [https://cinestage-1-0.onrender.com](https://cinestage-1-0.onrender.com)
- **API base URL:** `https://cinestage-1-0.onrender.com/api` (browsing and booking endpoints are public; admin endpoints need a JWT)

> **Please be patient on the first request.** The backend runs on Render's free plan and the database on Neon's free plan. After a period of inactivity, Render spins the backend down (after about 15 minutes) and Neon scales the database to zero. The first request then has to wake both up, which can take a couple of minutes (roughly 2 to 2.5 minutes). Nothing is broken and it will not crash. Once everything is awake, the app responds normally.

The customer experience (browse, filter, book, cancel) needs no login. The `/admin` console is protected by a private admin login, so admin actions are locked for visitors.

If you find an error, a bug, or any loophole while exploring the app, please let me know.

---

## 🏗️ Architecture Walkthrough

*A full walkthrough of the layered architecture — Controllers → Services → Repositories → Database — and how each entity connects to the others.*

https://github.com/user-attachments/assets/1e522e88-84a6-48eb-b531-6b0fdee271b8

---

## 🔄 Poster-Fetching Flow (TMDB Integration)

*A step-by-step walkthrough of how a movie gets its poster automatically: from the incoming request, through `MovieService` and `TmdbService`, out to the TMDB API over HTTP, and back into the database.*

https://github.com/user-attachments/assets/f038666a-784c-4321-9d23-e6e7fdf8fe0e

---

## 🧩 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java |
| Framework | Spring Boot |
| Security | Spring Security + JWT (jjwt) |
| Data Access | Spring Data JPA |
| Database | PostgreSQL (Neon, serverless) |
| Boilerplate Reduction | Lombok |
| External HTTP Calls | Spring `RestClient` + Apache HttpClient 5 |
| External API | [TMDB (The Movie Database)](https://www.themoviedb.org/) |
| Hosting | Render (backend), Neon (database), Netlify (frontend) |

---

## 🔗 Related Repositories

| Repo | Description |
|---|---|
| **Backend** (this repo) | Spring Boot REST API — bookings, scheduling, TMDB integration, admin auth |
| **Frontend** | [CineStage Frontend](https://github.com/Soumadeep1221/CineStage-Frontend) — Next.js client consuming this API |

**Frontend Tech Stack:**

| Layer | Technology |
|---|---|
| Framework | Next.js 16 (App Router) |
| UI Library | React 19 |
| Styling | Tailwind CSS 4 |
| Language | TypeScript |
| Package Manager | pnpm |
| Icons | Lucide React |
| Components | shadcn/ui |

---

## 🗂️ Domain Model

The system is built around 8 core entities:

```
City → Theatre → Screen → Seat
Movie + Screen → Show
User + Show + Seats (via booking_seats) → Booking
```

- **City, Theatre, Screen, Seat** — the physical location hierarchy
- **Movie** — movie catalog, poster fetched live from TMDB
- **Show** — a specific movie screening at a specific screen and time
- **User** — account holder making bookings
- **Booking** — links a user, a show, and one or more seats (`CONFIRMED` / `CANCELLED`)

Seats are typed as `REGULAR`, `PREMIUM`, or `VIP`.

---

## 🧱 Layered Structure

```
Controller Layer   → REST endpoints, request/response handling
Service Layer      → business logic, transactional operations
Repository Layer   → Spring Data JPA, derived + custom @Query methods
Entity Layer       → JPA-mapped PostgreSQL tables
```

Cross-cutting concerns:
- `GlobalExceptionHandler` (`@RestControllerAdvice`) — centralized error handling
- `SecurityConfig` — endpoint access rules, stateless sessions, and CORS for the CineStage frontend
- `JwtRequestFilter` + `JwtUtil` — validates the JWT on every request
- `AdminAuthController` — the admin login endpoint that issues the JWT

---

## 🔐 Admin Authentication

Customer-facing endpoints (browsing, search, booking) are fully public. Catalogue management (adding cities, theatres, screens, seats, movies, shows, editing or deleting movies, and viewing users) requires an admin JWT.

- There is exactly one admin, with credentials kept in environment variables, not in the database
- `POST /api/auth/admin/login` compares the submitted credentials with those values and returns a signed, expiring JWT
- `JwtRequestFilter` checks the token on every request, and `SecurityConfig` decides which endpoints require it
- Auth is stateless, with no server-side sessions

This was scoped on purpose around Spring Data JPA, so there is no user-table-based authentication (`UserDetailsService`, password hashing) for the admin account.

---

## 🎞️ TMDB Poster Integration

When a movie is added or updated, `MovieService` calls `TmdbService`, which:

1. Sends a search request to TMDB (`/search/movie`) with the movie's title and release year
2. Looks for an exact (case-insensitive) title match among the results
3. Falls back to TMDB's top result if no exact match is found
4. Extracts the poster path and builds the final poster URL
5. Returns it to be saved alongside the movie in PostgreSQL

This removed the earlier manual process of fetching TMDB JSON by hand and writing `UPDATE` statements — it's now fully automatic on every add/update.

---

## 🚢 Deployment

| Part | Platform |
|---|---|
| Frontend (Next.js) | Netlify |
| Backend (Spring Boot, Docker) | Render |
| Database (PostgreSQL) | Neon, serverless, Singapore region |

How it was deployed:

1. Created a Neon Postgres project and converted its connection string into a JDBC URL (`jdbc:postgresql://...?sslmode=require`) plus separate username and password
2. Deployed the backend to Render from this repo, with every secret set as an environment variable (nothing hardcoded)
3. Deployed the frontend to Netlify and pointed `NEXT_PUBLIC_API_BASE_URL` at the Render backend
4. Set the backend's `FRONTEND_URL` to the Netlify URL so CORS allows the frontend
5. Seeded the data through the admin panel

---

## 🚀 Getting Started

```bash
# clone the repo
git clone https://github.com/Soumadeep1221/CineStage-Backend.git
cd CineStage-Backend

# copy the example env file and fill in your own values
cp .env.example .env

# run
./mvnw spring-boot:run
```

Environment variables (see `.env.example`):

| Variable | Purpose |
|---|---|
| `DATASOURCE_URL` | JDBC URL, e.g. `jdbc:postgresql://host:5432/db?sslmode=require` |
| `DATASOURCE_USER` / `DATASOURCE_PASSWORD` | Database credentials |
| `FRONTEND_URL` | Frontend origin allowed by CORS |
| `TMDB_API_KEY` | TMDB API key for poster fetching |
| `ADMIN_USERNAME` / `ADMIN_PASSWORD` | The single admin's login |
| `JWT_SECRET` | Base64-encoded signing secret (e.g. `openssl rand -base64 32`) |
| `JWT_EXPIRATION_MS` | Token lifetime in milliseconds (default 1800000, 30 mins) |

---

## 📌 Known Limitations

- Customer accounts use register/login without token-based auth; JWT protects the admin endpoints only
- No AI integration; this project focuses on going deep on Spring Data JPA
- A valid admin token stays valid until it expires, since there is no server-side session to revoke
- The frontend was AI-generated, so it may have small rough edges; the backend was built from scratch
