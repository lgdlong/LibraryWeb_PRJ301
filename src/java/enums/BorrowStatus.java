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
        return BorrowStatus.valueOf(value.toUpperCase());
    }
}
