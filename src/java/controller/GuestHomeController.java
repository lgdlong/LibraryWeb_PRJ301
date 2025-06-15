/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import entity.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.BookService;

/**
 *
 * @author Dien Sanh
 */
public class GuestHomeController extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            BookService bookService = new BookService();
            List<Book> newBooks = bookService.getNewBooks();
            List<Book> availableBooks = bookService.getAvailableBook();

            request.setAttribute("newBooks", newBooks);
            request.setAttribute("availableBooks", availableBooks);
            request.setAttribute("contentPage", "/guest/guest.jsp");

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


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
