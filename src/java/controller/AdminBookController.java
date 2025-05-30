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
        String order = req.getParameter("order"); // "asc" or "desc", default is asc

        // Start with the full list of books
        List<Book> books = bookService.getAllBooks();

        // Apply search if provided
        if (search != null && !search.trim().isEmpty()) {
            books = bookService.searchByTitleOrAuthor(search.trim());
        }

        // Apply sorting if provided
        if (sort != null && !sort.trim().isEmpty()) {
            boolean ascending = !"desc".equalsIgnoreCase(order);
            books = bookService.sortInMemory(books, sort.trim(), ascending);
        }

        List<BookDTO> bookDTOs = books.stream()
            .map(BookMapping::toBookDTO)
            .collect(Collectors.toList());

        req.setAttribute("bookList", bookDTOs);
        req.setAttribute("pageTitle", "Book Management");
        req.setAttribute("contentPage", "/admin/book-management.jsp");
        req.getRequestDispatcher("/admin/layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String deleteId = req.getParameter("delete");

        try {
            if (deleteId != null && !deleteId.trim().isEmpty()) {
                handleDelete(deleteId);
            } else {
                handleCreateOrUpdate(req);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/books");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred processing your request");
        }
    }

    private void handleDelete(String deleteId) {
        long idToDelete = parseLongOrZero(deleteId);
        if (idToDelete > 0) {
            bookService.deleteBook(idToDelete);
        } else {
            throw new IllegalArgumentException("Invalid book ID for deletion");
        }
    }

    private void handleCreateOrUpdate(HttpServletRequest req) {
        long id = parseLongOrZero(req.getParameter("id"));
        String title = req.getParameter("title");
        String author = req.getParameter("author");
        String isbn = req.getParameter("isbn");
        String coverUrl = req.getParameter("coverUrl");
        String category = req.getParameter("category");
        int publishedYear = parseIntOrZero(req.getParameter("publishedYear"));
        int totalCopies = parseIntOrZero(req.getParameter("totalCopies"));
        int availableCopies = parseIntOrZero(req.getParameter("availableCopies"));
        String statusParam = req.getParameter("status");

        // Safe status parsing
        BookStatus status;
        try {
            status = statusParam != null ? BookStatus.fromString(statusParam) : BookStatus.ACTIVE;
        } catch (Exception e) {
            status = BookStatus.ACTIVE; // Default status
        }

        Book book = new Book(id, title, author, isbn, coverUrl, category,
            publishedYear, totalCopies, availableCopies, status);

        if (id == 0) {
            bookService.addBook(book);
        } else {
            bookService.updateBook(book);
        }
    }

    private long parseLongOrZero(String val) {
        try {
            return (val != null && !val.trim().isEmpty()) ? Long.parseLong(val.trim()) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private int parseIntOrZero(String val) {
        try {
            return (val != null && !val.trim().isEmpty()) ? Integer.parseInt(val.trim()) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
