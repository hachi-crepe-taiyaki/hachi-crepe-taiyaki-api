# Business rules — Hachi loyalty API

## Roles
- `funcao`: 0 = client, 1 = employee, 2 = admin.
- Public registration always creates a client (role 0). Never accept role, status, or stamp balance from registration input.
- Only admins can assign employee/admin roles. Clients must never elevate their own role.

## Authentication and users
- Passwords are stored only as BCrypt hashes; never return password hashes in API responses.
- Login returns a JWT in an HttpOnly cookie; logout expires that cookie.
- Registration and login are public. Logout, profile updates, and account deletion require valid authentication.
- A user may update/delete their own account. An admin may manage other accounts.
- JWT signing secret comes from environment configuration and must be long and unpredictable in production.
- Production cookies must use Secure and HTTPS. CORS must use explicit trusted origins with credentials, never wildcard origins.

## Loyalty flow
- Employees generate purchase codes after a qualifying restaurant purchase.
- A client redeems a code from their phone; successful redemption is recorded in `resgate_selo`.
- Each code may be redeemed only once. Enforce this transactionally in the redemption service.
- Each successful code redemption awards a stamp.
- At 12 stamps, a client may redeem a free item; each redemption is recorded in `resgate_item`.
- Feedback is associated with the authenticated user and stored in `feedback`.
- Apply ownership and role checks in services, not only controllers.
- Preserve database relationships and avoid deleting users in a way that silently breaks loyalty/feedback history; evaluate soft deletion or an explicit archival policy before enabling hard deletion.


## Java implementation conventions
- Use ordinary Java classes with fields, constructors, getters, and setters; do not use Java records.
- Avoid Optional chains using `.filter(...)` or `.orElseThrow(...)`. Prefer explicit repository methods and readable `if` checks.
- Name application methods in Portuguese and write user-facing validation and error messages in Portuguese.
- Keep JWT/authentication configuration classes under `com.hachi.api.config`; keep OpenAPI configuration under `com.hachi.api.config.swagger`.
- Keep the layered structure: controllers, DTO classes, mappers, repositories, and services.
