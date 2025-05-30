package enums;

public enum BookStatus {
    ACTIVE,
    INACTIVE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public static BookStatus fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("BookStatus value cannot be null or empty");
        }
        try {
            return BookStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid BookStatus: " + value, e);
        }
    }
}
