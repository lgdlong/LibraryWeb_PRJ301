package enums;

public enum UserStatus {
    ACTIVE,
    BLOCKED;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public static UserStatus fromString(String value) {
        return UserStatus.valueOf(value.toUpperCase());
    }
}
