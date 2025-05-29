package enums;

public enum PaidStatus {
    PAID,
    UNPAID;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public static PaidStatus fromString(String value) {
        return PaidStatus.valueOf(value.toUpperCase());
    }
}
