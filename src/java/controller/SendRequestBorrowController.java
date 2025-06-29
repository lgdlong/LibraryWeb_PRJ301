
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.Book;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.BorrowRecordService;


public class SendRequestBorrowController extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        User us = (User) session.getAttribute("LOGIN_USER");

        if(us == null){
            response.sendRedirect("Login.jsp");
            return;
        }

        List<Book> books = (List<Book>) session.getAttribute("borrowBook");
        if(books == null || books.isEmpty()){
            request.setAttribute("message","No books select to request");
            request.setAttribute("contentPage", "/guest/view-cart.jsp");
            request.setAttribute("sidebarPage", "/guest/my-library-sidebar.jsp");
            request.getRequestDispatcher("/guest/layout.jsp").forward(request, response);
            return;
        }
        try {
            BorrowRecordService service = new BorrowRecordService();
            int result = service.sendBorrowRequest(us.getId(),books);
            if(result > 0){
                request.setAttribute("message", "Borrow request sent successfully for ");
                session.removeAttribute("borrowBook");
            } else {
                request.setAttribute("message", "Failed to send borrow request. Please try again.");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("contentPage", "/guest/view-cart.jsp");
        request.setAttribute("sidebarPage", "/guest/my-library-sidebar.jsp");
        request.getRequestDispatcher("/guest/layout.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
