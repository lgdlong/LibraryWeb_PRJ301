package enums;

public enum UserRole {
    ADMIN,
    USER;

    @Override
    public String toString() {
        return name().toLowerCase(); // "admin" thay vì "ADMIN"
    }

    public static UserRole fromString(String value) {
        return UserRole.valueOf(value.toUpperCase());
    }
}
