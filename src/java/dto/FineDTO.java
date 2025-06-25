package dto;

public class FineDTO {
    private long id;
    private String userName;
    private String bookTitle;
    private long borrowRecordId; // để liên kết nghiệp vụ, nhưng tên là borrowRecordId cho rõ nghĩa
    private double fineAmount;
    private String paidStatus;

    public FineDTO() {
    }

    public FineDTO(long id, String userName, String bookTitle, long borrowRecordId, double fineAmount, String paidStatus) {
        this.id = id;
        this.userName = userName;
        this.bookTitle = bookTitle;
        this.borrowRecordId = borrowRecordId;
        this.fineAmount = fineAmount;
        this.paidStatus = paidStatus;
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

    public long getBorrowRecordId() {
        return borrowRecordId;
    }

    public void setBorrowRecordId(long borrowRecordId) {
        this.borrowRecordId = borrowRecordId;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public String getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(String paidStatus) {
        this.paidStatus = paidStatus;
    }
}
