# Library Web Application Development Guidelines

This document provides guidelines and instructions for developing and maintaining the Library Web Application project.

## Build and Configuration Instructions

### Prerequisites
- Docker and Docker Compose
- JDK 8 or later (for local development)
- Apache Ant (for local builds)

### Building the Application

#### Using Docker (Recommended)
The project uses Docker for both building and running the application:

1. **Build the WAR file and Docker image**:
   ```bash
   make build
   ```

2. **Start the application with the database**:
   ```bash
   make up
   ```

3. **Stop the application**:
   ```bash
   make down
   ```

4. **Full restart (including volumes)**:
   ```bash
   make restart
   ```

#### Using Ant (Local Development)
For local development without Docker:

1. **Compile the code**:
   ```bash
   ant compile
   ```

2. **Build the WAR file**:
   ```bash
   ant dist
   ```

3. **Clean the build artifacts**:
   ```bash
   ant clean
   ```

### Database Configuration

The application uses Microsoft SQL Server as its database. The database is automatically initialized with the script in `database/init.sql`.

To manage the database:

- **Start only the database**:
  ```bash
  make db-up
  ```

- **Stop the database**:
  ```bash
  make db-down
  ```

- **Backup the database initialization script**:
  ```bash
  make db-backup
  ```

## Testing Information

### Setting Up Tests

1. **Create test classes** in the `test` directory, following the same package structure as the source code.
2. **Name test classes** with a `Test` suffix (e.g., `UserTest.java`).
3. **Use JUnit 4** for writing tests.

### Running Tests

Run tests using the following command:

```bash
make test
```

This will:
1. Build a Docker image for testing
2. Run the tests using Ant inside the Docker container
3. Output the test results

### Example Test

Here's a simple example of a JUnit test for the `User` entity:

```java
package entity;

import enums.UserRole;
import enums.UserStatus;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testUserConstructor() {
        // Test the constructor with name, email, and password
        User user = new User("Test User", "test@example.com", "password123");

        assertEquals("Test User", user.getName());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals(UserRole.USER, user.getRole());
        assertEquals(UserStatus.ACTIVE, user.getUserStatus());
    }
}
```

### Adding New Tests

To add new tests:

1. Create a new test class in the appropriate package under the `test` directory
2. Write test methods annotated with `@Test`
3. Run the tests using `make test`

## Development Guidelines

### Project Structure

- **src/java/controller**: Servlet controllers for handling HTTP requests
- **src/java/dao**: Data Access Objects for database operations
- **src/java/db**: Database configuration
- **src/java/dto**: Data Transfer Objects
- **src/java/entity**: Domain model classes
- **src/java/enums**: Enumeration types
- **src/java/mapper**: Classes for mapping between entities and DTOs
- **src/java/service**: Business logic services

### Code Style

1. **Input Validation**:
   - Validate all input parameters in service methods
   - Use descriptive error messages in exceptions

2. **Error Handling**:
   - Use appropriate exceptions for different error cases
   - Handle exceptions at the controller level

3. **Naming Conventions**:
   - Use camelCase for variables and methods
   - Use PascalCase for class names
   - Use ALL_CAPS for constants

4. **Documentation**:
   - Document public methods with clear descriptions
   - Include parameter and return value descriptions

### Best Practices

1. **Service Layer**:
   - Implement business logic in service classes
   - Use services for validation and coordination

2. **DAO Layer**:
   - Keep database access code in DAO classes
   - Use prepared statements to prevent SQL injection

3. **Controllers**:
   - Keep controllers thin
   - Delegate business logic to services
   - Handle request parameters and session management

4. **Security**:
   - Validate user authentication in controllers
   - Check user roles for authorization
   - Sanitize user input to prevent XSS attacks
