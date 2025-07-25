package controller;

import entity.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.*;

import java.io.*;
import java.util.*;
@WebServlet("/borrow/book")
public class BorrowController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            HttpSession session = request.getSession();
            User us = (User) session.getAttribute("LOGIN_USER");
            if (us == null) {
                request.getRequestDispatcher("Login.jsp").forward(request, response);
            } else {
                String id = request.getParameter("bookId");
                Book b = null;
                if (id != null && !id.trim().isEmpty()) {
                    BookService service = new BookService();
                    b = service.getBookById(Long.parseLong(id));
                }
                if (b != null) {
                    ArrayList<Book> bookList = (ArrayList<Book>) session.getAttribute("borrowBook");
                    if (bookList == null) {
                        bookList = new ArrayList<>();
                        bookList.add(b);
                    } else {
                        boolean found = false;
                        for (Book book : bookList) {
                            if (book.getId() == b.getId()) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            bookList.add(b);
                        }
                    }
                    session.setAttribute("borrowBook", bookList);
                }
            }
            String referer = request.getHeader("referer");
            if (referer == null || referer.trim().isEmpty()) {
                referer = request.getContextPath() + "/home";
            }
            response.sendRedirect(referer);

        } catch (Exception e) {
            // Log the error appropriately
            getServletContext().log("Error in BorrowController", e);

            // Provide user feedback
            request.setAttribute("errorMessage", "An error occurred while processing your request. Please try again.");

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
