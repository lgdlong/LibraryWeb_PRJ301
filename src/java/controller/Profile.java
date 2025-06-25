/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.*;


public class Profile extends HttpServlet {

    private boolean isUserAuthenticated(HttpServletRequest request) {
        return request.getSession().getAttribute("LOGIN_USER") != null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        if (!isUserAuthenticated(request)) {
            response.sendRedirect(request.getContextPath() + "/GuestHomeController");
            return;
        }
        request.getRequestDispatcher("/view/view-profile.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // Handle profile update logic here
        // Then forward to the JSP page
        request.getRequestDispatcher("/view/view-profile.jsp").forward(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
