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
}
