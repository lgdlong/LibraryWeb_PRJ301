package service;

import dao.*;

public class BookService {
    private final BookDao bookDao = new BookDao();

    public long getTotalBooks() {
        return bookDao.countAll();
    }
}
