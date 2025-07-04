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

// Removed unused constants US and AD
//    private static final String ADMIN_PAGE = "/admin/layout.jsp";
//    private static final String USER_PAGE = "/user.jsp";
//    private static final String ERROR = "Login.jsp";


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
                 if (us.getUserStatus() == UserStatus.BLOCKED) {
                request.setAttribute("ERROR", "Your account is blocked. Please contact administrator.");
                request.getRequestDispatcher("/Login.jsp").forward(request, response);
                return; // Stop further processing
            }
                HttpSession session = request.getSession();
                session.setAttribute("LOGIN_USER", us);
                UserRole role = us.getRole();

                if (role.equals(UserRole.ADMIN)) {
                    response.sendRedirect(request.getContextPath() + "/admin");
                } else {
                    response.sendRedirect(request.getContextPath() + "/GuestHomeController");
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
