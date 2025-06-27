package controller;

import entity.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.BookService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "NewBookController", urlPatterns = {"/newBookController"})
public class NewBookController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            BookService bookService = new BookService();
            List<Book> newBooks = bookService.getNewBooks();


            HttpSession session = request.getSession();
            session.setAttribute("newBooks", newBooks);
            session.setAttribute("contentPage", "/guest/guest.jsp");

        } catch (Exception e) {
            System.err.println("Error in NewBookController: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving a new book.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controller displays a list of recently published books.";
    }
}
