<h1 align="center">🎬 CineStage <span align="right">— Backend</span></h1>

<p align="center">
  A full-featured movie ticket booking system backend — built with Spring Boot, PostgreSQL, and a live TMDB integration for real movie posters.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-007396?style=flat&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=flat&logo=postgresql&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=flat&logo=spring&logoColor=white" />
  <img src="https://img.shields.io/badge/TMDB%20API-01D277?style=flat&logo=themoviedatabase&logoColor=white" />
</p>

---

## 📖 About

**CineStage** is a cinema ticket booking platform — the kind of system that powers a real multiplex chain's ticket sales, built end-to-end. This repository is the **backend**: a Spring Boot REST API handling everything from theatre/screen/seat management to show scheduling, bookings, and live poster fetching from TMDB.

The frontend that consumes this API is **CineStage** (built separately, generated via AI frontend tooling and iterated on in VS Code).

---

## 🏗️ Architecture Walkthrough

*A full walkthrough of the layered architecture — Controllers → Services → Repositories → Database — and how each entity connects to the others.*

<!-- 🎥 PASTE ARCHITECTURE WALKTHROUGH VIDEO HERE -->

---

## 🔄 Poster-Fetching Flow (TMDB Integration)

*A step-by-step walkthrough of how a movie gets its poster automatically: from the incoming request, through `MovieService` and `TmdbService`, out to the TMDB API over HTTP, and back into the database.*

[<!-- 🎥 PASTE TMDB FLOW VIDEO HERE -->](https://github.com/user-attachments/assets/f038666a-784c-4321-9d23-e6e7fdf8fe0e)

---

## 🧩 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java |
| Framework | Spring Boot |
| Data Access | Spring Data JPA |
| Database | PostgreSQL |
| Boilerplate Reduction | Lombok |
| External HTTP Calls | Spring `RestClient` + Apache HttpClient 5 |
| External API | [TMDB (The Movie Database)](https://www.themoviedb.org/) |

---

## 🔗 Related Repositories

| Repo | Description |
|---|---|
| **Backend** (this repo) | Spring Boot REST API — bookings, scheduling, TMDB integration |
| **Frontend** | [CineStage Frontend](<paste-frontend-repo-link-here>) — Next.js client consuming this API |

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
Entity Layer        → JPA-mapped PostgreSQL tables
```

Cross-cutting concerns:
- `GlobalExceptionHandler` (`@RestControllerAdvice`) — centralized error handling
- `CorsConfig` — cross-origin access for the CineStage frontend

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

## 🚀 Getting Started

```bash
# clone the repo
git clone https://github.com/<your-username>/<your-repo>.git

# configure your database and TMDB API key in application.properties
spring.datasource.url=${DATASOURCE_URL}
spring.datasource.username=${DATASOURCE_USER}
spring.datasource.password=${DATASOURCE_PASSWORD}

frontend.url=${FRONTEND_URL}

tmdb.api-key=${TMDB_API_KEY}

# run
./mvnw spring-boot:run
```

---

## 📌 Roadmap / Known Limitations

- Role-based access control (admin vs. regular user) is not yet enforced at the backend level
- Auth token issuance is not yet implemented

---

## 👤 Author

Built by **Soumadeep** — documenting the full build process on [LinkedIn](https://linkedin.com/in/soumadeep-dey-).
