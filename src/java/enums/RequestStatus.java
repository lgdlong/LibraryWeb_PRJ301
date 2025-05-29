package enums;

public enum RequestStatus {
    PENDING,
    APPROVED,
    REJECTED;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public static RequestStatus fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("RequestStatus value cannot be null or empty");
        }
        try {
            return RequestStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid RequestStatus: " + value, e);
        }
    }
}
