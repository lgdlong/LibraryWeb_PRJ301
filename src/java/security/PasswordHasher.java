package security;

import org.mindrot.jbcrypt.*;

public class PasswordHasher {

    // Hash password với BCrypt
    public static String hash(String password) {
        // Cost 12 là hợp lý, có thể tăng giảm tùy server
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    // So sánh password raw với hash từ database
    public static boolean matches(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
