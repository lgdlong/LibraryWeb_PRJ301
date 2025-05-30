package service;

import dao.*;
import entity.*;

import java.util.*;

public class BookService {
    private final BookDao bookDao = new BookDao();

    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }

    public Book getBookById(long id) {
        return bookDao.getById(id);
    }

    public List<Book> searchByTitleOrAuthor(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return bookDao.searchByKeyword(keyword);
    }

    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book must not be null");
        }
        bookDao.add(book);
    }

    public void updateBook(Book book) {
        if (book == null || book.getId() <= 0) {
            throw new IllegalArgumentException("Book must not be null and must have a valid ID");
        }
        bookDao.update(book);
    }

    public void deleteBook(long id) {
        bookDao.delete(id);
    }

    public long getBookCount() {
        return bookDao.bookCount();
    }

    public List<Book> sortBooksBy(String field, boolean ascending) {
        return bookDao.sortBy(field, ascending);
    }

    public long getTotalBooks() {
        return bookDao.countAll();
    }
}

