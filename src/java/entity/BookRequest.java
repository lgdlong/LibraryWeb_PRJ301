package entity;

import enums.*;

import java.time.*;

public class BookRequest {
    private long id;
    private long userId;
    private long bookId;
    private LocalDate requestDate;
    private RequestStatus status;

    // Default constructor
    public BookRequest() {
    }

    // Constructor for creating a book request
    public BookRequest(long userId, long bookId, LocalDate requestDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.requestDate = requestDate;
        this.status = RequestStatus.PENDING; // Default status
    }

    // Constructor for retrieving a book request from the database
    public BookRequest(long id, long userId, long bookId, LocalDate requestDate, RequestStatus status) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.requestDate = requestDate;
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

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }
}
