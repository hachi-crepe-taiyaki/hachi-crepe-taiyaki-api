# Business rules — Hachi loyalty API

## Roles
- `funcao`: 0 = client, 1 = employee, 2 = admin.
- Public registration always creates a client (role 0). Never accept role, status, or stamp balance from registration input.
- Only admins can assign employee/admin roles. Clients must never elevate their own role.

## Authentication and users
- Passwords are stored only as BCrypt hashes; never return password hashes in API responses.
- Login returns a JWT in an HttpOnly cookie; logout expires that cookie.
- Registration and login are public. Logout, profile updates, account deletion, and user listing require valid authentication.
- User listing supports optional filters by email, telephone, birth date, city, region, and name. Email, telephone, and name use case-insensitive contains matching; birth date uses exact matching; city and region use case-insensitive exact matching. Multiple filters are combined with AND.
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

## Additional loyalty rules
- Submitting feedback requires a valid JWT and an existing active user; associate the feedback with the authenticated user rather than accepting a user ID from the request body.
- Listing feedback requires authentication. Results may be filtered independently or together by user ID, start date, and end date; date boundaries are inclusive by calendar day, and results are ordered newest first.
- Feedback must include an integer grade from 0 through 5 and a non-blank comment of at most 255 characters. Store the submission timestamp and user relationship.
- Only an authenticated, active employee (`funcao=1` and `status_usuario=1`) may generate purchase codes.
- Code generation requires the purchase amount. The amount must be at least R$ 40,00 and fit the database's `DECIMAL(5,2)` range (up to R$ 999,99).
- Generated purchase codes are unique, six-character uppercase alphanumeric values and start unused (`statusScan=0`). A successful redemption marks the code used (`statusScan=1`); used or unknown codes cannot award another stamp.
- Code redemption requires an authenticated, active client and is allowed only for that client's own account. Trim surrounding whitespace and compare codes case-insensitively.
- A successful code redemption must atomically create a `resgate_selo` history record, add exactly one stamp to the user's balance, and mark the code as used. If any step fails, the operation must roll back.
- Redeeming a free item requires an authenticated, active user redeeming for their own account and a balance of at least 12 stamps. Record the redemption in `resgate_item` and subtract exactly 12 stamps; preserve any remaining balance. The redemption record is distinct from `resgate_selo`, which records earning a stamp.
- A user's loyalty-balance changes must not update `atualizado_em`; that timestamp is reserved for changes to personal data.

## Java implementation conventions
- Use ordinary Java classes with fields, constructors, getters, and setters; do not use Java records.
- Avoid Optional chains using `.filter(...)` or `.orElseThrow(...)`. Prefer explicit repository methods and readable `if` checks.
- Name application methods in Portuguese and write user-facing validation and error messages in Portuguese.
- Keep JWT/authentication configuration classes under `com.hachi.api.config`; keep OpenAPI configuration under `com.hachi.api.config.swagger`.
- Use only the JJWT library and APIs/patterns from the provided `config.zip` for JWT operations (`Jwts`, `Claims`, `Decoders.BASE64`, `Keys.hmacShaKeyFor`, signed-claims parsing); do not add a different JWT library or alternative token implementation.
- Repository queries must use Spring Data JPA derived method names; do not use `@Query` or `@Param` for application repository methods.
- Keep the layered structure: controllers, DTO classes, mappers, repositories, and services.
