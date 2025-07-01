package service;

import dao.*;
import entity.*;

import java.sql.*;
import java.util.*;
import java.util.stream.*;

public class BookService {
    private final BookDao bookDao = new BookDao();

    public boolean isBookAvailable(long bookId) {
        if (bookId <= 0) {
            throw new IllegalArgumentException("Book ID must be positive");
        }
        Book book = bookDao.getById(bookId);
        return book != null && book.getAvailableCopies() > 0;
    }

    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }

    public Book getBookById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Book ID must be positive");
        }
        return bookDao.getById(id);
    }

    public List<Book> searchByTitleOrAuthor(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllBooks(); // Return all books if no keyword
        }
        return bookDao.searchByKeyword(keyword.trim());
    }

    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book must not be null");
        }
        validateBook(book);
        bookDao.add(book);
    }

    public void updateBook(Book book) {
        if (book == null || book.getId() <= 0) {
            throw new IllegalArgumentException("Book must not be null and must have a valid ID");
        }
        validateBook(book);
        bookDao.update(book);
    }

    public void deleteBook(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Book ID must be positive");
        }
        bookDao.delete(id);
    }

    public List<Book> sortInMemory(List<Book> books, String field, boolean ascending) {
        if (books == null) {
            return new ArrayList<>();
        }
        // Filter out null books to prevent NPE
        List<Book> validBooks = books.stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        Comparator<Book> comparator;
        String normalizedField = field.trim().toLowerCase();
        switch (normalizedField) {
            case "title":
                comparator = Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER);
                break;
            case "author":
                comparator = Comparator.comparing(Book::getAuthor, String.CASE_INSENSITIVE_ORDER);
                break;
            case "publishedyear":
            case "published_year":  // Support both formats
                comparator = Comparator.comparingInt(Book::getPublishedYear);
                break;
            case "totalcopies":
                comparator = Comparator.comparingInt(Book::getTotalCopies);
                break;
            case "availablecopies":
                comparator = Comparator.comparingInt(Book::getAvailableCopies);
                break;
            default:
                throw new IllegalArgumentException("Invalid sort field: " + field);
        }
        if (!ascending) {
            comparator = comparator.reversed();
        }
        List<Book> sortedList = new ArrayList<>(validBooks);
        sortedList.sort(comparator);
        return sortedList;
    }

    private void validateBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Author is required");
        }
        if (book.getTotalCopies() < 0) {
            throw new IllegalArgumentException("Total copies cannot be negative");
        }
        if (book.getAvailableCopies() < 0) {
            throw new IllegalArgumentException("Available copies cannot be negative");
        }
        if (book.getAvailableCopies() > book.getTotalCopies()) {
            throw new IllegalArgumentException("Available copies cannot exceed total copies");
        }
        if (book.getPublishedYear() < 1000 || book.getPublishedYear() > Calendar.getInstance().get(Calendar.YEAR) + 1) {
            throw new IllegalArgumentException("Invalid published year");
        }
    }

    public long getTotalBooks() {
        return bookDao.bookCount();
    }

    public ArrayList<Book> getNewBooks() {
        return bookDao.getNewBooks();
    }

    public List<Book> searchBookByKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return null;
        }
        return bookDao.searchBookByKeyword(keyword.trim());
    }

    public List<Book> getAvailableBook() {
        return bookDao.getAvailableBook();
    }

    public boolean incrementAvailableCopies(long bookId) {
        if (bookId <= 0) {
            throw new IllegalArgumentException("Book ID must be positive");
        }
        Book b = bookDao.increaseBookAvailable(bookId);
        return b != null && b.getAvailableCopies() >= 0;
    }

    public boolean decrementAvailableCopies(long bookId) {
        if (bookId <= 0) {
            throw new IllegalArgumentException("Book ID must be positive");
        }
        Book b = bookDao.decreaseBookAvailable(bookId);
        return b != null && b.getAvailableCopies() >= 0;
    }

    public boolean decrementAvailableCopies(Connection conn, long bookId) {
        if (bookId <= 0) {
            throw new IllegalArgumentException("Book ID must be positive");
        }
        if (conn == null) {
            throw new IllegalArgumentException("Connection must not be null");
        }
        Book b = bookDao.decreaseBookAvailable(conn, bookId);
        return b != null && b.getAvailableCopies() >= 0;
    }
}
