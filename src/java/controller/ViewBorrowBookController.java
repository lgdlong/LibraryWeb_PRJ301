/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Book;
import entity.User;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Dien Sanh
 */
public class ViewBorrowBookController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            HttpSession session = request.getSession();
            User us = (User) session.getAttribute("LOGIN_USER");
            if(us == null){
                response.sendRedirect("Login.jsp");
                return;
            }
                ArrayList<Book> list = (ArrayList<Book>) session.getAttribute("borrowBook");
                String action = request.getParameter("action");
                String id = request.getParameter("bookId");
                if(list == null){
                    list = new ArrayList<>();
                }
                if("remove".equals(action)){
                    long bookId = Long.parseLong(id);
                    list.removeIf(book -> book.getId()==bookId);
                    session.setAttribute("borrowBook", list);
                    response.sendRedirect("ViewBorrowBookController");
                    return;
                }

                request.setAttribute("borrowBook", list);
                request.setAttribute("contentPage", "/guest/view-cart.jsp");
                request.setAttribute("sidebarPage", "/guest/my-library-sidebar.jsp");
                request.getRequestDispatcher("/guest/layout.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
