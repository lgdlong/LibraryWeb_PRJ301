package controller;

import entity.*;
import enums.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import service.*;

import java.io.*;
import java.sql.*;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.getRequestDispatcher("/Login.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            AuthService authService = new AuthService();
            User us = authService.checkLogin(email, password);

            if (us != null) {
                // Check if the user is blocked
                if (UserStatus.BLOCKED.equals(us.getUserStatus())) {
                    request.setAttribute("ERROR_ATTRIBUTE", "Your account is blocked, Please contact administrator.");
                    request.getRequestDispatcher("/Login.jsp").forward(request, response);
                    return; // Stop further processing
                }

                HttpSession session = request.getSession();
                session.setAttribute("LOGIN_USER", us);

                if (UserRole.ADMIN.equals(us.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/admin");
                } else {
                    response.sendRedirect(request.getContextPath() + "/home");
                }
            } else {
                request.setAttribute("ERROR", "Incorrect email or password");
                request.getRequestDispatcher("/Login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            log("ERROR at LoginController: " + e.toString());
            request.setAttribute("ERROR", "Internal server error");
            request.getRequestDispatcher("/Login.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
