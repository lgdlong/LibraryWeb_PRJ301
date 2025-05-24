package entity;

import enums.*;

public class Fine {
    private long id;
    private long borrowId;
    private double fineAmount;
    private PaidStatus paidStatus;

    public Fine() {
    }

    public Fine(long borrowId, double fineAmount) {
        this.borrowId = borrowId;
        this.fineAmount = fineAmount;
        paidStatus = PaidStatus.UNPAID; // Default to UNPAID status
    }

    public Fine(long id, long borrowId, double fineAmount, PaidStatus paidStatus) {
        this.id = id;
        this.borrowId = borrowId;
        this.fineAmount = fineAmount;
        this.paidStatus = paidStatus;
    }

    public long getId() {
        return id;
    }

    public long getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(long borrowId) {
        this.borrowId = borrowId;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public PaidStatus getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(PaidStatus paidStatus) {
        this.paidStatus = paidStatus;
    }
}
