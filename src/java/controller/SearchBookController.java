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
import jakarta.servlet.http.HttpSession;
import service.BookService;


public class SearchBookController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        String keyword = request.getParameter("keyword");
        if (keyword != null && !keyword.trim().isEmpty()) {
            session.setAttribute("searchKeyword", keyword);
        } else {
            keyword = (String) session.getAttribute("searchKeyword");
        }
        if (keyword == null ||keyword.trim().isEmpty()) {
            BookService bookService = new BookService();
            List<Book> availableBooks = bookService.getAllActiveBooks();

            request.setAttribute("availableBooks", availableBooks);
            request.setAttribute("isSearch", false);
            request.setAttribute("contentPage","/guest/guest.jsp");
            request.getRequestDispatcher("/guest/layout.jsp").forward(request,response);
        }else{
            session.setAttribute("searchKeyword",keyword);
            List<Book> books;
            BookService service = new BookService();
            books = service.searchBookByKeyword(keyword);

            request.setAttribute("results", books);
            request.setAttribute("contentPage","/guest/guest-search.jsp");
            request.getRequestDispatcher("/guest/layout.jsp").forward(request,response);
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
