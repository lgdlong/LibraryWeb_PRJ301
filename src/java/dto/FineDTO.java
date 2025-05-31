package dto;

public class FineDTO {
    private long id;
    private String username;
    private String bookTitle;
    private double fineAmount;
    private String paidStatus;

    public FineDTO() {
    }

    public FineDTO(long id, String username, String bookTitle, double fineAmount, String paidStatus) {
        this.id = id;
        this.username = username;
        this.bookTitle = bookTitle;
        this.fineAmount = fineAmount;
        this.paidStatus = paidStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
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
