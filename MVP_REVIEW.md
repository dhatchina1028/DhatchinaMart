# DhatchinaMart — MVP Review

Status: **MVP complete** — all core flows implemented, verified end-to-end, and documented.

## Verification Summary (all green)

| Layer                    | Result |
| ------------------------ | ------ |
| Unit + integration tests | **29/29 passed** (`mvn clean package`) |
| Black-box E2E checks     | **30/30 passed** against the deployed WAR (buyer, seller, admin journeys + RBAC) |
| Build                    | Single `dhatchinamart.war`, `BUILD SUCCESS` |

## Who Can Log In

Only `admin@dhatchinamart.com / Admin@123` is pre-seeded. Every other account is
self-registered on the sign-up page as a Buyer or Seller — credentials are stored
(bcrypt-hashed) in the H2 database and work on future logins. Roles are fixed at
registration.

## What's In (MVP scope)

- Full buyer journey: self-register → login → browse → search → category filter →
  product details → add to cart → quantity update (AJAX JSON) → mock checkout →
  order created (PENDING) → order history → order details.
- Seller journey: self-register as Seller → login → dashboard (own products,
  counts) → create / edit / delete products with server-side validation.
- Admin journey: login → dashboard (total users, sellers, products, orders, revenue)
  → activate / deactivate users → recent orders.
- Stock integrity: quantity checked + decremented atomically at order time
  (`UPDATE ... WHERE stock_qty >= ?`), over-buying blocked, cart clamped to stock.
- Security: session + role filters (302 login redirect, 403 on wrong role),
  BCrypt password hashing, CSRF tokens on all state-changing forms, escaping on
  all user output, PreparedStatements everywhere, custom 404s.
- Reusable embedded H2 DB (file-based, auto schema + seed on first boot).
- Catalog: 40 seed products across 5 categories (Accessories, Books, Clothing,
  Electronics, Home — 8 each), owned by the platform admin; registered sellers
  add their own.

## Notable Bug Found & Fixed During Verification

- **Case-sensitive search** — `LOWER(name) LIKE ?` compared against an
  un-lowercased pattern, so any query with uppercase letters returned no results.
  Fixed by lowercasing the escaped pattern in `ProductDAOImpl.escapeLike`.
- **JSP money formatting** — `pattern="#,##0.00"` used the server default locale
  (`en_IN`), producing inconsistent grouping. Replaced with
  `type="number" minFractionDigits="2" maxFractionDigits="2"` plus a pinned
  `en_US` locale and `pageEncoding="UTF-8"` so ₹ prices render identically on
  every page. Verified in the E2E output (`1,299.00`, `2,598.00`, `5,196.00`).

## What's Out (post-MVP / next iterations)

- Real payment integration (currently mock confirm).
- Order status transitions by seller (mark DELIVERED / CANCELLED) and order
  cancellation by buyer — statuses exist in the schema and render in the UI.
- Search pagination (large catalogs), product images are remote placeholders.
- Email notifications, password reset, "forgot password".
- Wishlists, reviews/ratings, coupon codes.
- Image upload / storage (currently `imageUrl` text field).
- Dashboard charts; today's numbers are counts only.
- H2 → MySQL/PostgreSQL swap (DAO layer isolates the SQL; the app runs on
  H2-in-file mode for demo portability).

## Run Instructions

1. `mvn clean package`
2. Copy `target/dhatchinamart.war` into Tomcat 9's `webapps/` and start Tomcat.
3. Open `http://localhost:9090/dhatchinamart` (see README — admin is seeded; everyone else self-registers).
