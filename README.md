# DhatchinaMart

A full-stack Java EE marketplace MVP with three roles: **Buyer**, **Seller**, and **Admin**. Built with JSP/Servlets (Java 11+), JSTL, HikariCP + H2, Tomcat 9, and hand-rolled CSS. No front-end frameworks, no Spring — pure Java EE, all SQL written by hand.

## Demo Accounts

Only the platform admin is pre-created. Everyone else **self-registers** on the
sign-up page — as a **Buyer** ("Shop for products") or a **Seller** ("Sell my
products"). Registered accounts are stored in the H2 database and persist across
restarts; log in again any time with the same email + password.

| Role  | Email                     | Password    | Notes                              |
| ----- | ------------------------- | ----------- | ---------------------------------- |
| Admin | `admin@dhatchinamart.com` | `Admin@123` | Only pre-seeded account            |

Sign-up asks for name, email, password + account type. Roles are fixed at
registration (a Buyer cannot become a Seller and vice-versa) — an admin can
deactivate any account.

## Features

**Buyer**
- Register / login / logout — sign up as a Buyer or Seller; accounts persist in the database (sessions, `BCrypt` password hashing, login throttling)
- Browse, keyword search, and category filter on the product catalog
- Product details, add-to-cart, quantity updates (AJAX JSON + progressive-enhancement forms)
- Checkout with mock payment, order history, order details (PENDING / DELIVERED / CANCELLED statuses)
- Stock is reserved and decremented atomically at order time; over-buying is blocked

**Seller**
- Dashboard with per-seller product counts
- Create / edit / delete own products (cannot touch other sellers' products)

**Admin**
- Dashboard with global metrics (total users, sellers, products, orders, revenue)
- User management (activate / deactivate), recent orders list

**Security**
- Role-based access control via a filter chain: `AuthFilter` (session + redirect to login) and `RoleFilter` (403 for wrong role)
- CSRF tokens on state-changing forms, HTML/JS escaping on all user output (`<c:out>`)
- Server-side validation everywhere; cart quantities clamped to stock; `404` for unknown products
- SQL injection safe by construction (PreparedStatements only)

## Tech Stack

- **Java 11** (compiled with `--release 11`), **Jakarta-era `javax.*`** servlets
- **Tomcat 9.0.x**, **JSP 2.3 / JSTL 1.2**
- **H2** embedded file database (auto-create schema + seed on first run)
- **HikariCP** connection pool
- **JUnit 5 + Mockito** unit/integration tests
- **Maven** build → single deployable WAR

## Project Layout

```
src/main/java/com/dhatchina/dhatchinamart/
  controller/    Servlets (Auth, Product, Cart, Checkout, Order, Seller, Admin)
  dao/           JDBC DAOs (users, products, cart, orders)
  model/         POJOs
  service/       Business logic (auth, products, cart, orders)
  util/          DbUtil (Hikari pool), Security (BCrypt + CSRF)
  filter/        AuthFilter, RoleFilter, EncodingFilter
src/main/webapp/WEB-INF/jsp/   Views (products, cart, checkout, seller, admin, ...)
db/schema.sql, db/seed.sql     DDL + demo data (loaded on first boot)
```

## Build & Run

```bash
# 1. Build (runs 27 unit/integration tests)
mvn clean package

# 2. Deploy to Tomcat 9
cp target/dhatchinamart.war <TOMCAT_HOME>/webapps/

# 3. Open
http://localhost:8080/dhatchinamart
```

### Configuration (env vars, all optional)

| Variable            | Default                              |
| ------------------- | ------------------------------------ |
| `DHAT_DB_URL`       | `jdbc:h2:file:~/dhatchinamart;AUTO_SERVER=TRUE` |
| `DHAT_DB_USER`      | `sa`                                 |
| `DHAT_DB_PASSWORD`  | _(empty)_                            |
| `DHAT_DB_POOL_MAX`  | `10`                                 |

On first boot the app creates all tables (from `db/schema.sql`) and seeds the
admin account plus a starter catalog of **40 products across 5 categories**
(Accessories, Books, Clothing, Electronics, Home — 8 each, from `db/seed.sql`).
Remove the DB file to reset all registered accounts and data.

## Testing

```bash
mvn test                # 27 unit + integration tests (in-memory H2)
```

A black-box end-to-end script (`e2e-buyer-flow.ps1`) exercises the full buyer /
seller / admin journeys against a running deployment — **30 checks**, all passing:
self-registration (buyer + seller) → login → browse → search → filter → details →
cart (add + AJAX update) → checkout → order created → history → stock decremented →
cart cleared → RBAC (403 / redirect) → seller create → admin dashboard.
