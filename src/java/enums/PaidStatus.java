package enums;

public enum PaidStatus {
    PAID,
    UNPAID;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public static PaidStatus fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("PaidStatus value cannot be null or empty");
        }
        try {
            return PaidStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid PaidStatus: " + value, e);
        }
    }
}
