package controller;

import entity.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.*;

import java.io.*;
import java.util.*;

@WebServlet("/search")
public class SearchBookController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String keyword = request.getParameter("keyword");
        BookService bookService = new BookService();

        if (keyword == null || keyword.trim().isEmpty()) {

            List<Book> availableBooks = bookService.getAllActiveBooks();

            request.setAttribute("availableBooks", availableBooks);
            request.setAttribute("isSearch", false);


            request.setAttribute("contentPage", "/guest/guest.jsp");
            request.getRequestDispatcher("/guest/layout.jsp").forward(request, response);
            return;
        }

        // Nếu có keyword
        List<Book> results = bookService.searchBookByKeyword(keyword);

        request.setAttribute("searchKeyword", keyword);
        request.setAttribute("results", results);
        request.setAttribute("isSearch", true);
        request.setAttribute("contentPage", "/guest/guest-search.jsp");

        request.getRequestDispatcher("/guest/layout.jsp").forward(request, response);



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
