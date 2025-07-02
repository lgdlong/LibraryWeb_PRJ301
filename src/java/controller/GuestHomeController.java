package controller;

import entity.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import service.*;

import java.io.*;
import java.util.*;

/**
 * @author Dien Sanh
 */
public class GuestHomeController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            BookService bookService = new BookService();
            List<Book> newBooks = bookService.getNewBooks();
            List<Book> availableBooks = bookService.getAvailableBook();

            HttpSession session = request.getSession();
            session.setAttribute("newBooks", newBooks);
            session.setAttribute("availableBooks", availableBooks);
            session.setAttribute("contentPage", "/guest/guest.jsp");

            request.getRequestDispatcher("/guest/layout.jsp").forward(request, response);

        } catch (Exception e) {
            response.sendError(500, "Error loading home page.");
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
