package enums;

public enum BookStatus {
    AVAILABLE,
    UNAVAILABLE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public static BookStatus fromString(String value) {
        return BookStatus.valueOf(value.toUpperCase());
    }
}
