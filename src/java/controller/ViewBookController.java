package controller;

import entity.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.*;

import java.io.*;

@WebServlet("/view")
public class ViewBookController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String idStr = request.getParameter("id");
            if (idStr == null || idStr.trim().isEmpty()) {
                response.sendRedirect("GuestHomeController");
                return;
            }
            int id = Integer.parseInt(idStr);
            BookService service = new BookService();
            Book book = service.getBookById(id);

            HttpSession session = request.getSession();
            session.setAttribute("book", book);
            session.setAttribute("contentPage", "/guest/guest-view.jsp");
            request.getRequestDispatcher("/guest/layout.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            System.err.println("Invalid book ID format: " + request.getParameter("id"));
            response.sendRedirect(request.getContextPath() + "/GuestHomeController");
        } catch (Exception e) {
            System.err.println("Error in ViewBookController: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving the book.");
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
