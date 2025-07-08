# Password Migration Guide

## Overview
A one-time password migration method has been added to `LoginController.doGet()` to automatically hash all plain text passwords in the database using BCrypt.

## How It Works

### Detection Logic
The migration uses a heuristic to detect plain text passwords:
- Passwords with length < 20 characters OR
- Passwords that don't contain the '$' character (BCrypt hashes always contain '$')

### Migration Process
1. Fetches all users from the database
2. For each user, checks if their password appears to be plain text
3. If plain text is detected:
   - Hashes the password using `PasswordHasher.hash()` (BCrypt with cost 12)
   - Updates the user record in the database
   - Logs the migration for that user

## How to Run the Migration

### Step 1: Deploy and Access
1. Deploy your application to Tomcat
2. Navigate to the login page (e.g., `http://localhost:8080/yourapp/login`)
3. The migration will run automatically when the login page loads

### Step 2: Check the Logs
Monitor your application logs (Tomcat console/logs) for migration output:
```
=== Starting Password Migration ===
Total users to check: 5
Migrated password for user: admin@library.com (ID: 1)
Migrated password for user: user@library.com (ID: 2)
=== Password Migration Complete ===
Total passwords migrated: 2
IMPORTANT: Remove the migratePasswordsToHash() method call from LoginController.doGet
```

### Step 3: Remove the Migration Code (IMPORTANT!)
After the migration has run successfully, you MUST remove the migration code to prevent it from running again:

1. Open `src/java/controller/LoginController.java`
2. Remove these lines from the `doGet()` method:
   ```java
   // ONE-TIME PASSWORD MIGRATION - REMOVE AFTER RUNNING ONCE
   migratePasswordsToHash();
   // END ONE-TIME PASSWORD MIGRATION
   ```
3. Remove the entire `migratePasswordsToHash()` method (lines ~29-71)
4. Redeploy the application

### Step 4: Verify Migration Success
- Try logging in with existing user accounts using their original plain text passwords
- The login should work normally as the system will now hash the input password and compare it with the stored BCrypt hash

## Safety Features

### Idempotent Operation
- The migration is safe to run multiple times
- It only processes passwords that appear to be plain text
- Already hashed passwords are left unchanged

### Error Handling
- Comprehensive error logging
- Individual user failures don't stop the entire migration
- Detailed logging for troubleshooting

### Backup Recommendation
Before running the migration, consider backing up your users table:
```sql
-- Create backup
SELECT * INTO users_backup FROM users;

-- Or export to file
-- Use your database's export functionality
```

## Technical Details

### BCrypt Configuration
- Uses BCrypt with cost factor 12 (configurable in `PasswordHasher.java`)
- Generates unique salt for each password
- Results in 60-character hashes starting with "$2a$12$"

### Authentication Compatibility
- The existing `AuthService.checkLogin()` method already uses `PasswordHasher.matches()`
- No changes needed to login logic after migration
- Both old plain text and new hashed passwords will work during transition

## Troubleshooting

### Migration Doesn't Run
- Check if the login page loads correctly
- Verify application logs for error messages
- Ensure database connectivity

### Some Passwords Not Migrated
- Check the detection heuristic (length < 20 OR no '$')
- Review logs for specific user errors
- Manually verify password formats in database

### Login Issues After Migration
- Verify `AuthService` uses `PasswordHasher.matches()`
- Check for any custom password validation logic
- Review authentication flow logs

## Post-Migration Cleanup

After successful migration and verification:

1. **Remove migration code** from LoginController
2. **Update documentation** to reflect BCrypt usage
3. **Update user registration** to ensure it uses BCrypt (should already be implemented)
4. **Consider password policy updates** since BCrypt allows longer passwords securely

## Security Notes

- BCrypt is designed to be slow (cost factor 12 ≈ ~250ms per hash)
- Each password gets a unique salt automatically
- The migration preserves the ability for users to log in with their original passwords
- Consider implementing password complexity requirements for new passwords
