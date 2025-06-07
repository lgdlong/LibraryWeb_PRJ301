package dto;

import java.time.*;

public class BookRequestDTO {
    private long id;
    private String userName;
    private String bookTitle;
    private LocalDate requestDate;
    private String status;

    public BookRequestDTO() {
    }

    public BookRequestDTO(long id, String userName, String bookTitle, LocalDate requestDate, String status) {
        this.id = id;
        this.userName = userName;
        this.bookTitle = bookTitle;
        this.requestDate = requestDate;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
