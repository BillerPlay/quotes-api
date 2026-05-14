# Quotes API
![Actions app ci](https://github.com/BillerPlay/quotes-api/actions/workflows/ci.yml/badge.svg)

A production-ready Spring Boot REST API for managing quotes, built with Java 17, MySQL (local), PostgreSQL (production), and deployed on Render.

🚀 **Live Demo:** `https://quotes-api-iyuq.onrender.com/`

📋 **Project Board:** [Trello](https://trello.com/invite/b/6a030943e050b18e994707b1/ATTI24c56b9e164ed39f1bf4bfa236c6a5816E6BDF3D/quotes-api)

🤖 **Postman collection:** [Postman](https://documenter.getpostman.com/view/52828273/2sBXqQFy1y) 

---

## API Reference

Base URL: `/api/quotes`

All requests and responses use `Content-Type: application/json`.

---

### Create a Quote

```
POST /api/quotes
```

**Request body:**

```json
{
   "author": "Marcus Aurelius",
   "text": "The impediment to action advances action. What stands in the way becomes the way.",
   "category": "philosophy"
}
```

**Responses:**
- `201 Created` — returns the created quote as JSON
- `400 Bad Request` — validation failed

---

### Get a Quote by ID

```
GET /api/quotes/:id
```

**Responses:**
- `200 OK` — returns the quote as JSON
- `404 Not Found` — quote with that ID does not exist

---

### Update a Quote

```
PUT /api/quotes/:id
```

**Request body** (include only fields to update):

```json
{
   "category": "stoicism"
}
```

**Responses:**
- `200 OK` — returns the updated quote as JSON
- `400 Bad Request` — validation failed
- `404 Not Found` — quote with that ID does not exist

---

### Delete a Quote

```
DELETE /api/quotes/:id
```

**Responses:**
- `204 No Content` — quote deleted successfully
- `404 Not Found` — quote with that ID does not exist

---

### List All Quotes

```
GET /api/quotes
```

**Query Parameters:**

| Parameter | Type | Required | Description |
|---|---|---|---|
| `search` | string | No | Filters quotes by keyword (matches author, text, or category — case-insensitive) |
| `page` | integer | No | Page number, zero-based (default: `0`) |
| `size` | integer | No | Number of results per page (default: `20`) |
| `sort` | string | No | Sort field and direction, e.g. `createdAt,desc` |

**Examples:**

```
GET /api/quotes                          → all quotes, first page
GET /api/quotes?search=stoic            → quotes matching "stoic"
GET /api/quotes?page=1&size=5           → second page, 5 per page
GET /api/quotes?search=marcus&size=10   → search with custom page size
```

**Responses:**
- `200 OK` — returns a paginated object

```json
{
  "content": [
    {
      "id": 1,
      "author": "Marcus Aurelius",
      "text": "The impediment to action advances action.",
      "category": "philosophy",
      "createdAt": "2026-05-12T10:00:00"
    }
  ],
  "totalElements": 42,
  "totalPages": 5,
  "size": 10,
  "number": 0
}
```
---
## 🔒 Soft Delete Feature

This project uses **Soft Delete** instead of permanently removing data from the database.

*   **How it works:** When you delete a quote, it isn't actually gone. Instead, a hidden flag (`is_deleted`) is set to `true`.
*   **Why?** This prevents accidental data loss and allows for easy data recovery if needed.
*   **Hibernate 6 @SoftDelete:** We used the latest Hibernate feature to handle this automatically. When you try to get quotes, the system is smart enough to only show you the ones that haven't been deleted yet.
---
## Local Development (Docker)

### Prerequisites

- [Docker](https://www.docker.com/get-started) installed

### Run locally

Docker Compose starts both MySQL and the app with a single command:

```bash
git clone https://github.com/BillerPlay/quote-management-system.git
cd quote-management-system
docker compose up --build
```

The API will be available at `http://localhost:8080`.

### Stop everything

```bash
docker compose down
```

---

## Spring Profiles

| Profile | Database | Used for |
|---|---|---|
| `default` | MySQL on `localhost:3306` | Local development |
| `test` | H2 in-memory | Integration tests |
| `prod` | PostgreSQL (Render) | Cloud deployment |

---

## CI/CD

GitHub Actions runs on every push or PR to `main`:

1. Checkout code
2. Set up Java 17
3. Cache Maven dependencies
4. Run `mvn clean verify` (builds + runs all tests with H2)

The pipeline status is shown as a badge on the repo.

---

## Deployment (Render)

### Step 1 — Create a PostgreSQL Database

1. Go to [dashboard.render.com](https://dashboard.render.com)
2. Click **"New +"** → select **PostgreSQL**
3. Configure:

   | Field | Value |
         |---|---|
   | Name | `quote-management-db` |
   | Region | e.g. **Frankfurt (EU Central)** — note it for Step 2 |
   | PostgreSQL Version | `16` |

4. Click **"Create Database"** and wait until status is **Available**
5. Copy the **Internal Database URL** — you'll need it in Step 2

> ⚠️ **Important:** the database and the web service must be deployed to the **same region**. Render's internal networking only works within a region.

---

### Step 2 — Deploy the Web Service

1. Click **"New +"** → select **Web Service**
2. Connect your GitHub account and select:
   ```
   BillerPlay/quote-management-system
   ```
3. Configure:

   | Field | Value |
   |---|---|
   | Name | `quote-management-system` |
   | Region | **Same as your database** |
   | Environment | **Docker** |
   | Dockerfile Path | `./Dockerfile` |

4. Under the **"Environment"** tab, add:

   | Key | Value |
   |---|---|
   | `SPRING_DATASOURCE_URL` | from Render DB |
   | `SPRING_DATASOURCE_USERNAME` | from Render DB |
   | `SPRING_DATASOURCE_PASSWORD` | from Render DB |
   | `SPRING_PROFILES_ACTIVE` | `prod` |

5. Click **"Create Web Service"**
6. Once deployed, copy your Render URL and paste it at the top of this README

### Auto-deploys

Every push to `main` triggers a new build and deploy automatically.

---

## Contributors

| Name                  | GitHub                                       |
|-----------------------|----------------------------------------------|
| Abdulvahhab Alaskarov | [@BillerPlay](https://github.com/BillerPlay) |
| Islam Samadov         | [@IslamSamadov](https://github.com/IslamSamadov) |
| Lala Aliyeva          | [@lalocchi](https://github.com/lalocchi)     |