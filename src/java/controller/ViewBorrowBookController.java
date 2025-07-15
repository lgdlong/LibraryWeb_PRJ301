package controller;

import entity.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;
import java.util.*;

@WebServlet("/borrow/current")
public class ViewBorrowBookController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            HttpSession session = request.getSession();

            ArrayList<Book> list = (ArrayList<Book>) session.getAttribute("borrowBook");
            String action = request.getParameter("action");
            String id = request.getParameter("bookId");
            if (list == null) {
                list = new ArrayList<>();
            }
            if ("remove".equals(action)) {
                long bookId = Long.parseLong(id);
                list.removeIf(book -> book.getId() == bookId);
                session.setAttribute("borrowBook", list);
                response.sendRedirect("ViewBorrowBookController");
                return;
            }

            request.setAttribute("borrowBook", list);
            request.setAttribute("contentPage", "/user/view-cart.jsp");
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
