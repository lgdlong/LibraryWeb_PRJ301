package enums;

public enum BorrowStatus {
    BORROWED,
    RETURNED,
    OVERDUE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public static BorrowStatus fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("BorrowStatus value cannot be null or empty");
        }
        try {
            return BorrowStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid BorrowStatus: " + value, e);
        }
    }
}
