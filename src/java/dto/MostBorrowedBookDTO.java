package dto;

public class MostBorrowedBookDTO {
    private long bookId;
    private String bookTitle;
    private long borrowCount;

    public MostBorrowedBookDTO() {}

    public MostBorrowedBookDTO(long bookId, String bookTitle, long borrowCount) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.borrowCount = borrowCount;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public long getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(long borrowCount) {
        this.borrowCount = borrowCount;
    }
}
