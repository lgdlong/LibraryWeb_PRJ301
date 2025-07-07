package entity;

import enums.*;
import service.*;

import java.time.*;

public class BorrowRecord {
    private final SystemConfigService systemConfigService = new SystemConfigService();

    private long id;
    private long userId;
    private long bookId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private BorrowStatus status;

    public BorrowRecord() {
    }

    public BorrowRecord(long userId, long bookId) {
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = LocalDate.now();
        setDueDate(borrowDate);
        this.returnDate = null; // Initially, the return date is null
        this.status = BorrowStatus.BORROWED; // Default status when a record is created
    }

    public BorrowRecord(long id, long userId, long bookId, LocalDate borrowDate, LocalDate dueDate, LocalDate returnDate, BorrowStatus status) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate borrowDate) {
        if (borrowDate == null) {
            throw new IllegalArgumentException("Borrow date must not be null");
        }
        SystemConfig config = systemConfigService.getConfigByConfigKey("default_borrow_duration_days");
        if (config == null) {
            throw new IllegalStateException("Configuration for default_borrow_duration_days not found");
        }
        long defaultBorrowDurationDays = (long) config.getConfigValue();
        this.dueDate = borrowDate.plusDays(defaultBorrowDurationDays);
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public BorrowStatus getStatus() {
        return status;
    }

    public void setStatus(BorrowStatus status) {
        this.status = status;
    }

    public boolean isOverdue() {
        if (this.dueDate == null) {
            throw new IllegalStateException("Due date is not set. Cannot check overdue status.");
        }
        LocalDate now = LocalDate.now();
        return BorrowStatus.BORROWED.equals(this.status) && this.dueDate.isBefore(now);
    }

    public void markAsReturned() {
        // Check if the record is already returned or if the return date is already set
        if (BorrowStatus.RETURNED.equals(this.status)) {
            throw new IllegalStateException("Record is already marked as returned. Cannot mark as returned again.");
        }
        if (this.returnDate != null) {
            throw new IllegalStateException("Return date is already set. Cannot mark as returned again.");
        }
        this.returnDate = LocalDate.now();
        this.status = BorrowStatus.RETURNED;
    }

    @Override
    public String toString() {
        return "BorrowRecord{" +
            "systemConfigService=" + systemConfigService +
            ", id=" + id +
            ", userId=" + userId +
            ", bookId=" + bookId +
            ", borrowDate=" + borrowDate +
            ", dueDate=" + dueDate +
            ", returnDate=" + returnDate +
            ", status=" + status +
            '}';
    }
}
