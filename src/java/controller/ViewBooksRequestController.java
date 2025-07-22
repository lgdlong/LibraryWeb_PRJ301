package controller;

import entity.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.*;

import java.io.*;
import java.util.*;
@WebServlet("/borrow/requested")
public class ViewBooksRequestController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            HttpSession session = request.getSession();
            User us = (User) session.getAttribute("LOGIN_USER");

            BookRequestService service = new BookRequestService();
            List<BookRequest> list = service.viewBooksRequest(us.getId());
            BookService bookService = new BookService();

            Map<Long, String> bookTitles = new HashMap<>();
            for (BookRequest req : list) {
                long bookId = req.getBookId();
                if (!bookTitles.containsKey(bookId)) {
                    Book book = bookService.getBookById(bookId);
                    if (book != null) {
                        bookTitles.put(bookId, book.getTitle());
                    }
                }
            }

            request.setAttribute("booksRequest", list);
            request.setAttribute("bookTitles", bookTitles);
            request.setAttribute("contentPage", "/user/view-borrow-book.jsp");
            request.setAttribute("sidebarPage", "/user/my-library-sidebar.jsp");
            request.getRequestDispatcher("/guest/layout.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);
    }
}
