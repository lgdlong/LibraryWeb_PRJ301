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
public class GuestAvailableBookController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            BookService bookService = new BookService();
            List<Book> availableBooks = bookService.getAvailableBook();

            HttpSession session = request.getSession();
            session.setAttribute("availableBooks", availableBooks);
            session.setAttribute("contentPage", "/guest/guest.jsp");

            // Forward to the common layout
            request.getRequestDispatcher("/guest/layout.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("Error in GuestAvailableBookController: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving books.");
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
