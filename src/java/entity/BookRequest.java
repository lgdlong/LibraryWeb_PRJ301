package entity;

import enums.*;

public class BookRequest {
    private long id;
    private long userId;
    private long bookId;
    private String requestDate;
    private RequestStatus status;

    // Default constructor
    public BookRequest() {
    }

    // Constructor for creating a book request
    public BookRequest(long userId, long bookId, String requestDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.requestDate = requestDate;
        this.status = RequestStatus.PENDING; // Default status
    }

    // Constructor for retrieving a book request from the database
    public BookRequest(long id, long userId, long bookId, String requestDate, RequestStatus status) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.requestDate = requestDate;
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

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }
}
