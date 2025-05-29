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
        return RequestStatus.valueOf(value.toUpperCase());
    }
}
