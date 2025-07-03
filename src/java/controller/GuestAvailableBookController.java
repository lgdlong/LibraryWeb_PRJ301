/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
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
public class GuestAvailableBookController extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            BookService bookService = new BookService();
            List<Book> availableBooks = bookService.getAllActiveBooks();



            request.setAttribute("availableBooks", availableBooks);
            String page = request.getParameter("page");
            if(page == null || page.isEmpty()){
                page = "guest.jsp";
            }
            request.setAttribute("contenPage","/guest/" + page);

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


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
