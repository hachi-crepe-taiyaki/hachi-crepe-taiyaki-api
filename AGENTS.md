# Business rules — Hachi loyalty API

- `funcao`: 0 = client, 1 = employee, 2 = admin.
- Public registration creates clients only; role, status, and stamp balance are server-controlled.
- Passwords are stored only as BCrypt hashes, never plaintext.
- Login issues a short-lived JWT in an HttpOnly cookie; logout clears the cookie.
- Registration and login must remain public so users can start a session. Logout, profile updates, and account deletion require a valid JWT.
- A user may update or delete their own account. Admins may manage other accounts. Clients cannot grant themselves roles or change their stamp balance.
- Employees issue purchase codes; a client redeems a code into `resgate_selo`.
- Each valid code may be redeemed only once; enforce this transactionally when the code workflow is implemented.
- One stamp is earned per successfully redeemed code. When the balance reaches 12, the client may redeem a free item; each redemption is recorded in `resgate_item`.
- Feedback belongs to the authenticated user and is stored in `feedback`.
- Never expose password hashes in API responses.
- JWT signing secrets must be supplied through environment configuration in production. Use HTTPS and secure cookies in production.
- Browser origins must be explicitly allowlisted for credentialed CORS; do not use wildcard origins with credentials.
