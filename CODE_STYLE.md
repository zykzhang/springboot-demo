# Company Java Backend Code Style Guide

This document defines the coding conventions for Java Spring Boot
projects using Oracle SQL in our company.\
All developers **must follow** these rules to ensure consistency,
maintainability, and quality across the codebase.\
Comments and documentation **must be written in English**.

------------------------------------------------------------------------

## 1. File Header & Licensing

-   Each source file must start with a header stating file ownership and
    the license.

✅ **Valid Example:**

``` java
/**
 * Copyright (c) 2025 OurCompany. All rights reserved.
 * Licensed under the Company License.
 */
public class FileSender {}
```

❌ **Invalid Example:**

``` java
// no license, no ownership
public class FileSender {}
```

------------------------------------------------------------------------

## 2. Documentation & Javadoc

-   Public classes, methods, and fields (API) must be documented with
    Javadoc.
-   Guidelines:
    -   Include `@author`
    -   Include **creation date**
    -   At least 1 line of description
    -   All parameters documented with `@param`
    -   Checked exceptions documented with `@throws`
    -   Return values documented with `@return` if not `void`
-   Comment density should reach **at least 15%**.

✅ **Valid Example:**

``` java
/**
 * Sends a file to the remote server.
 *
 * @author Ethan
 * @created 2025-08-25
 * @param fileName the name of the file to send
 * @throws IOException if the file cannot be sent
 * @return true if sending succeeds, false otherwise
 */
public boolean sendFile(String fileName) throws IOException {
    return true;
}
```

❌ **Invalid Example:**

``` java
// send file
public boolean sendFile(String fileName) throws IOException {
    return true;
}
```

------------------------------------------------------------------------

## 3. Naming Conventions

-   **Package names**: lowercase, regex
    `^[a-z_]+(\.[a-z_][a-z0-9_]*)*$`\
    Example: `com.company.tt.boi.batchservicecommon`

-   **Domain Objects**: `com.company.tt.boi.drr.entity`

-   **DTOs**: `com.company.tt.boi.drr.dto`

-   **Class names**: CamelCase, regex `^[A-Z][a-zA-Z0-9]*$`\
    Example: `FileSender`

-   **Abstract classes**: prefix `Abstract`\
    Example: `AbstractFileSender`

-   **Methods, fields, variables**: camelCase\
    Example: `fileSender`

-   **Test classes**: suffix `Test`\
    Example: `FileSenderTest`

-   **Test methods**: prefix `test`\
    Example: `testSendFile()`

-   **Constants and enums**: UPPER_CASE with `_`\
    Example: `MAX_SIZE`

✅ **Valid Example:**

``` java
public class FileSender {
    private String filePath;
    public static final int MAX_SIZE = 100;

    public void sendFile() {}
}
```

❌ **Invalid Example:**

``` java
public class file_sender {
    private String FilePath;
    public static int maxSize = 100;

    public void Send_file() {}
}
```

------------------------------------------------------------------------

## 4. Unit Testing

-   Minimum **85% line coverage** and **branch coverage**
-   Tests must include **assertions**
-   Must test **boundary conditions** and **exceptions**

✅ **Valid Example:**

``` java
@Test
public void testSendFile_Success() {
    FileSender sender = new FileSender();
    assertTrue(sender.sendFile("data.txt"));
}

@Test(expected = IOException.class)
public void testSendFile_ThrowsException() throws IOException {
    FileSender sender = new FileSender();
    sender.sendFile(null);
}
```

❌ **Invalid Example:**

``` java
@Test
public void testSendFile() {
    FileSender sender = new FileSender();
    sender.sendFile("data.txt"); // no assertion
}
```

------------------------------------------------------------------------

## 5. Timezone & Auditing

-   Always use **UTC timezone**
-   Set JVM args: `-Duser.timezone=UTC`
-   Auditing class must extend `AuditingEntity`

✅ **Valid Example:**

``` java
@Entity
public class UserLog extends AuditingEntity {}
```

❌ **Invalid Example:**

``` java
@Entity
public class UserLog {}
```

------------------------------------------------------------------------

## 6. Charset & Locale

-   Default **charset = UTF-8**
-   Default **locale = en_US**
-   Do not rely on system default

✅ **Valid Example:**

``` java
new String(bytes, StandardCharsets.UTF_8);
"ABCD".getBytes(StandardCharsets.UTF_8);
```

❌ **Invalid Example:**

``` java
new String(bytes, Charset.defaultCharset());
"ABCD".getBytes(Charset.defaultCharset());
```

------------------------------------------------------------------------

## 7. Database Type Mapping

-   Java → Oracle SQL mapping:
    -   `boolean` → `NUMBER(1,0)`
    -   `String (ASCII)` → `VARCHAR2(size BYTE)`
    -   `String (Unicode)` → `NVARCHAR2(size)`
    -   `byte` → `NUMBER(3)`
    -   `short` → `NUMBER(5)`
    -   `int` → `NUMBER(10)`
    -   `long` → `NUMBER(19)`
    -   `BigInteger` → `NUMBER(38)`
    -   `float` → `NUMBER` (TBD)
    -   `double` → `NUMBER` (TBD)
    -   `BigDecimal` → `NUMBER` (TBD)

✅ **Valid Example:**

``` sql
CREATE TABLE users (
    id NUMBER(19) PRIMARY KEY,
    name NVARCHAR2(100),
    active NUMBER(1,0)
);
```

❌ **Invalid Example:**

``` sql
CREATE TABLE users (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    active BOOLEAN
);
```

------------------------------------------------------------------------

## 8. Exception Handling

-   All fields in an Exception class must be `final`
-   Use **try-with-resources**

✅ **Valid Example:**

``` java
public class MyException extends Exception {
    private final String errorCode;

    public MyException(String errorCode) {
        this.errorCode = errorCode;
    }
}
```

❌ **Invalid Example:**

``` java
public class MyException extends Exception {
    private String errorCode; // not final
}
```

✅ **Valid Example (try-with-resources):**

``` java
try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))) {
    return br.readLine();
}
```

❌ **Invalid Example:**

``` java
BufferedReader br = new BufferedReader(new FileReader("file.txt"));
try {
    return br.readLine();
} finally {
    br.close();
}
```

------------------------------------------------------------------------

## 9. Code Quality & Static Analysis

-   All code must pass **SonarQube checks** with **no major issues**
-   Avoid:
    -   Hard-coded values
    -   Magic numbers
    -   Deprecated APIs
    -   Complex nested loops (\>3 levels)

------------------------------------------------------------------------

## 10. Logging

-   Use **SLF4J** with `logback`
-   No `System.out.println`
-   Log at correct levels (`INFO`, `WARN`, `ERROR`)

✅ **Valid Example:**

``` java
private static final Logger log = LoggerFactory.getLogger(FileSender.class);
log.info("File sent successfully");
```

❌ **Invalid Example:**

``` java
System.out.println("File sent successfully");
```

------------------------------------------------------------------------

## 11. Code Formatting

-   **Indentation**: 4 spaces (no tabs)
-   **Max line length**: 120 chars
-   **Braces**: K&R style (`{` on the same line)
-   **One statement per line**
-   **No trailing whitespace**

✅ **Valid Example:**

``` java
if (condition) {
    doSomething();
} else {
    doSomethingElse();
}
```

❌ **Invalid Example:**

``` java
if (condition)
    { doSomething(); } else { doSomethingElse(); }
```

------------------------------------------------------------------------

## 12. Security Best Practices

-   Never log sensitive data (passwords, tokens, PII)
-   Use **PreparedStatement** for SQL (no string concatenation)
-   Use **parameterized logging** (`{}` instead of `+`)

✅ **Valid Example:**

``` java
String sql = "SELECT * FROM users WHERE id = ?";
PreparedStatement stmt = conn.prepareStatement(sql);
stmt.setInt(1, userId);
```

❌ **Invalid Example:**

``` java
String sql = "SELECT * FROM users WHERE id = " + userId;
Statement stmt = conn.createStatement();
```

------------------------------------------------------------------------

# Summary

-   **Consistency** is key\
-   **Readable, maintainable, and testable code** is required\
-   **Follow these conventions strictly** before committing code
