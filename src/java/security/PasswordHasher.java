package security;

import org.mindrot.jbcrypt.*;

public class PasswordHasher {

    // Hash password với BCrypt
    public static String hash(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        // Cost 12 là hợp lý, có thể tăng giảm tùy server
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    // So sánh password raw với hash từ database
    public static boolean matches(String rawPassword, String hashedPassword) {
        if (rawPassword == null || hashedPassword == null) {
            return false;
        }
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
