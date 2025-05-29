package enums;

public enum UserStatus {
    ACTIVE,
    BLOCKED;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public static UserStatus fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("UserStatus value cannot be null or empty");
        }
        try {
            return UserStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UserStatus: " + value, e);
        }
    }
}
