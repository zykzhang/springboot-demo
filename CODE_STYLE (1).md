# Java Spring Boot & Oracle SQL Coding Standards

This document defines the coding style guidelines for backend development using Java Spring Boot and Oracle SQL.  
All comments **MUST** be written in English.  
The specification is inspired by Alibaba Java Development Manual, adapted for our company context.

---

## 1. File Header & License

### Requirement
- Every source file **MUST** start with a license header, including copyright, author, and date.
- Use standard company template.

### Implementation
- Configure IDE (IntelliJ/Eclipse) to automatically insert license headers.

### Example
✅ Correct:
```java
/*
 * Copyright (c) 2025 Company Name
 * Author: Ethan Zhang
 * Created: 2025-08-25
 */
public class UserService { ... }
```

❌ Incorrect:
```java
// no license header
public class UserService { ... }
```

### Tool Support
- IntelliJ IDEA: `Settings -> File and Code Templates`.
- SonarLint: can check for missing headers.

---

## 2. Javadoc Specification

### Requirement
- All public classes and methods must have Javadoc.
- Javadoc should describe **purpose**, **parameters**, **return value**, and **exceptions**.

### Implementation
- Use `/** ... */` format, not `//` or `/* ... */`.
- Include `@param`, `@return`, `@throws` tags.

### Example
✅ Correct:
```java
/**
 * Get user by ID.
 *
 * @param userId user identifier
 * @return User entity
 * @throws UserNotFoundException if no user found
 */
public User getUserById(Long userId) { ... }
```

❌ Incorrect:
```java
// get user
public User getUserById(Long id) { ... }
```

### Tool Support
- Checkstyle with Javadoc module.
- SonarQube rules for missing Javadoc.

---

## 3. Naming Conventions

### Requirement
- Class names: PascalCase (e.g., `UserService`).
- Method and variable names: camelCase (e.g., `getUserInfo`).
- Constants: UPPER_CASE with underscores (e.g., `MAX_RETRY_COUNT`).
- Database tables: lowercase with underscores (e.g., `user_account`).
- Database columns: lowercase with underscores (e.g., `created_at`).

### Example
✅ Correct:
```java
public class OrderController {
    private static final int MAX_RETRY_COUNT = 3;
    private String orderName;
}
```

❌ Incorrect:
```java
public class ordercontroller {
    private static final int MaxRetryCount = 3;
    private String OrderName;
}
```

---

## 4. Unit Test Requirements

### Requirement
- Every service method must have at least one unit test.
- Unit tests must be independent and repeatable.

### Implementation
- Use JUnit 5 and Mockito.
- Test class name must match the class under test (e.g., `UserServiceTest`).

### Example
✅ Correct:
```java
@Test
void shouldReturnUserWhenExists() {
    User mockUser = new User(1L, "Alice");
    when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

    User result = userService.getUserById(1L);
    assertEquals("Alice", result.getName());
}
```

❌ Incorrect:
```java
@Test
void test1() {
    assert true;
}
```

### Tool Support
- Jacoco for coverage reports.
- SonarQube: enforce minimum test coverage.

---

## 5. Timezone

### Requirement
- Always use UTC timezone for backend logic.
- Convert to local timezone only at presentation layer.

### Example
✅ Correct:
```java
ZonedDateTime nowUtc = ZonedDateTime.now(ZoneOffset.UTC);
```

❌ Incorrect:
```java
ZonedDateTime now = ZonedDateTime.now(); // system default, not UTC
```

---

## 6. Charset & Locale

### Requirement
- Always use `UTF-8` charset.
- Always specify locale explicitly (avoid system default).

### Example
✅ Correct:
```java
String content = new String(bytes, StandardCharsets.UTF_8);
Locale locale = Locale.US;
```

❌ Incorrect:
```java
String content = new String(bytes); // default charset
Locale locale = Locale.getDefault();
```

---

## 7. Database Type Mapping

### Requirement
- Oracle `NUMBER` → Java `BigDecimal` or `Long` (not `float/double`).
- Oracle `DATE` → Java `LocalDateTime`.
- Oracle `CHAR(1)` → Java `String` (not `char`).

### Example
✅ Correct:
```java
@Column(name = "amount")
private BigDecimal amount;
```

❌ Incorrect:
```java
@Column(name = "amount")
private double amount;
```

---

## 8. Exception Class Fields

### Requirement
- Custom exception classes must define:
  - `errorCode`
  - `message`
- Avoid generic `Exception`/`RuntimeException`.

### Example
✅ Correct:
```java
public class BusinessException extends RuntimeException {
    private final String errorCode;
    public BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
```

❌ Incorrect:
```java
public class MyException extends Exception {}
```

---

## 9. Try-with-resources

### Requirement
- Always use try-with-resources for JDBC, IO, Streams.

### Example
✅ Correct:
```java
try (Connection conn = dataSource.getConnection();
     PreparedStatement ps = conn.prepareStatement("SELECT * FROM users")) {
    ...
}
```

❌ Incorrect:
```java
Connection conn = null;
try {
    conn = dataSource.getConnection();
} finally {
    if (conn != null) conn.close();
}
```

---

## 10. Toolchain Integration

### Requirement
- Enforce style checks via CI/CD.
- Reject commits that do not follow coding standards.

### Tools
- Checkstyle: enforce code format.
- PMD: detect bad practices.
- SpotBugs: detect common bugs.
- SonarQube: code quality and coverage reports.
- Pre-commit hooks: run `mvn clean verify` before pushing.

---

## 11. SQL Standards

### Requirement
- SQL keywords must be uppercase.
- Table aliases must be meaningful (not single letters).
- Avoid `SELECT *`, always list columns explicitly.

### Example
✅ Correct:
```sql
SELECT u.user_id, u.user_name
FROM user_account u
WHERE u.status = 'ACTIVE';
```

❌ Incorrect:
```sql
select * from user_account a where a.status = 'ACTIVE';
```

---

## 12. REST API Standards

### Requirement
- Use nouns for resource names (`/users`, `/orders`).
- Use plural form.
- Use correct HTTP methods:
  - GET: read
  - POST: create
  - PUT: update
  - DELETE: delete

### Example
✅ Correct:
```
GET /api/v1/users/123
POST /api/v1/users
```

❌ Incorrect:
```
GET /api/v1/getUser?id=123
POST /api/v1/createUser
```

---

## 13. Logging

### Requirement
- Use SLF4J + Logback.
- Never use `System.out.println`.
- Log messages must include context (IDs, parameters).

### Example
✅ Correct:
```java
log.info("User created: id={}, name={}", user.getId(), user.getName());
```

❌ Incorrect:
```java
System.out.println("user created");
```

---

## 14. Security

### Requirement
- Never log sensitive information (passwords, tokens).
- Always validate input parameters.
- Use prepared statements to prevent SQL injection.

### Example
✅ Correct:
```java
String sql = "SELECT * FROM users WHERE username = ?";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setString(1, username);
```

❌ Incorrect:
```java
String sql = "SELECT * FROM users WHERE username = '" + username + "'";
Statement stmt = conn.createStatement();
stmt.executeQuery(sql);
```

---

## 15. Code Review Process

### Requirement
- Every merge request must be reviewed by at least one senior developer.
- Checklist must include:
  - Naming conventions
  - Unit tests
  - Logging
  - Security
  - Documentation

---

End of Document.
