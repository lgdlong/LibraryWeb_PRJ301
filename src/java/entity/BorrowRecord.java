package entity;

import enums.*;

import java.time.*;

public class BorrowRecord {
    private long id;
    private long userId;
    private long bookId;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private long dueDate;
    private BorrowStatus status; // e.g., "BORROWED", "RETURNED", "OVERDUE"

    public BorrowRecord() {
    }

    public BorrowRecord(long userId, long bookId, LocalDateTime borrowDate, LocalDateTime returnDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        setDueDate(borrowDate);
        this.status = BorrowStatus.BORROWED;
    }

    public BorrowRecord(long id, long userId, long bookId, LocalDateTime borrowDate, LocalDateTime returnDate, long dueDate, BorrowStatus status) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    public long getId() {
        return id;
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

    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDateTime borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime borrowDate) {
        LocalDateTime now = LocalDateTime.now();
        this.dueDate = Duration.between(borrowDate, now).toDays();
    }

    public BorrowStatus getStatus() {
        return status;
    }

    public void setStatus(BorrowStatus status) {
        this.status = status;
    }
}
