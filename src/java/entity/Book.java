package entity;

import enums.*;

public class Book {
    private long id;
    private String title;
    private String author;
    private String isbn;
    private String coverUrl;
    private String category;
    private int publishedYear;
    private int totalCopies;
    private int availableCopies;
    private BookStatus status;

    public Book() {
    }

    public Book(String title, String author, String isbn, String coverUrl, String category, int publishedYear, int totalCopies) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.coverUrl = coverUrl;
        this.category = category;
        this.publishedYear = publishedYear;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies; // Default to 0 available copies
        this.status = BookStatus.ACTIVE; // Default status
    }

    public Book(long id, String title, String author, String isbn, String coverUrl, String category, int publishedYear, int totalCopies, int availableCopies, BookStatus status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.coverUrl = coverUrl;
        this.category = category;
        this.publishedYear = publishedYear;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }
}
