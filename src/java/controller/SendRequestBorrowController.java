package controller;

import entity.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import service.*;

import java.io.*;
import java.util.*;


public class SendRequestBorrowController extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        User us = (User) session.getAttribute("LOGIN_USER");

        if (us == null) {
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }

        List<Book> books = (List<Book>) session.getAttribute("borrowBook");
        if (books == null || books.isEmpty()) {
            session.setAttribute("message", null);
            request.setAttribute("contentPage", "/user/view-cart.jsp");
            request.setAttribute("sidebarPage", "/user/my-library-sidebar.jsp");
            request.getRequestDispatcher("/guest/layout.jsp").forward(request, response);
            return;
        }

        try {
            BorrowRecordService service = new BorrowRecordService();
            int result = service.sendBorrowRequest(us.getId(), books);
            if (result > 0) {
                request.setAttribute("message", "Borrow request sent successfully for selected books.");
                session.removeAttribute("borrowBook");
            } else {
                request.setAttribute("message", "Failed to send borrow request. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", "An error occurred. Please try again.");
        }

        request.setAttribute("contentPage", "/user/view-cart.jsp");
        request.setAttribute("sidebarPage", "/user/my-library-sidebar.jsp");
        request.getRequestDispatcher("/guest/layout.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
