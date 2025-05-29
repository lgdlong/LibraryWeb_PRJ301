package enums;

public enum UserRole {
    ADMIN,
    USER;

    @Override
    public String toString() {
        return name().toLowerCase(); // "admin" thay vì "ADMIN"
    }

    public static UserRole fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("UserRole value cannot be null or empty");
        }
        try {
            return UserRole.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UserRole: " + value, e);
        }
    }
}
