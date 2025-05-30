package controller;

import dto.*;
import entity.*;
import enums.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import mapper.*;
import service.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

@WebServlet("/admin/books")
public class AdminBookController extends HttpServlet {
    private final BookService bookService = new BookService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter("search");
        String sort = req.getParameter("sort");

        List<BookDTO> books;

        if (search != null && !search.trim().isEmpty()) {
            books = bookService.searchByTitleOrAuthor(search.trim()).stream()
                .map(BookMapping::toBookDTO)
                .collect(Collectors.toList());
        } else {
            books = bookService.getAllBooks().stream()
                .map(BookMapping::toBookDTO)
                .collect(Collectors.toList());
        }

        // Simple sort
        if ("title".equalsIgnoreCase(sort)) {
            books.sort(Comparator.comparing(BookDTO::getTitle, String.CASE_INSENSITIVE_ORDER));
        } else if ("year".equalsIgnoreCase(sort)) {
            books.sort(Comparator.comparingInt(BookDTO::getPublishedYear));
        }

        req.setAttribute("bookList", books);
        req.setAttribute("pageTitle", "Book Management");
        req.setAttribute("contentPage", "/admin/book-management.jsp");
        req.getRequestDispatcher("/admin/layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String deleteId = req.getParameter("delete");

        try {
            if (deleteId != null) {
                long idToDelete = parseLongOrZero(deleteId);
                if (idToDelete > 0) {
                    bookService.deleteBook(idToDelete);
                } else {
                    throw new IllegalArgumentException("Invalid book ID for deletion");
                }
            } else {
                long id = parseLongOrZero(req.getParameter("id"));
                String title = req.getParameter("title");
                String author = req.getParameter("author");
                String isbn = req.getParameter("isbn");
                String coverUrl = req.getParameter("coverUrl");
                String category = req.getParameter("category");
                int publishedYear = parseIntOrZero(req.getParameter("publishedYear"));
                int totalCopies = parseIntOrZero(req.getParameter("totalCopies"));
                int availableCopies = parseIntOrZero(req.getParameter("availableCopies"));
                BookStatus status = BookStatus.fromString(req.getParameter("status"));

                Book book = new Book(id, title, author, isbn, coverUrl, category,
                    publishedYear, totalCopies, availableCopies, status);

                if (id == 0) {
                    bookService.addBook(book);
                } else {
                    bookService.updateBook(book);
                }
            }

            resp.sendRedirect(req.getContextPath() + "/admin/books");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid book operation");
        }
    }

    private long parseLongOrZero(String val) {
        try {
            return (val != null && !val.isEmpty()) ? Long.parseLong(val) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private int parseIntOrZero(String val) {
        try {
            return (val != null && !val.isEmpty()) ? Integer.parseInt(val) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
