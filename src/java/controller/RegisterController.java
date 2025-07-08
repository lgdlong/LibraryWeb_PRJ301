package controller;

import dto.*;
import entity.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import security.*;
import service.*;

import java.io.*;
import java.sql.*;

@WebServlet("/register")
public class RegisterController extends HttpServlet {

    private static final String REGISTER_PAGE = "Register.jsp";
    private static final String SUCCESS_PAGE = "Login.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        request.getRequestDispatcher(REGISTER_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        UserError userError = new UserError();
        UserService userService = new UserService();
        AuthService authService = new AuthService();
        boolean check = true;

        try {
            String email = request.getParameter("email");
            String fullName = request.getParameter("fullname");
            String password = request.getParameter("password");
            String confirm = request.getParameter("confirm");

            // Validate input
            if (fullName.length() < 5 || fullName.length() > 30) {
                userError.setNameError("Fullname must be 5-30 characters.");
                check = false;
            }

            if (password.length() < 5 || password.length() > 30) {
                userError.setPasswordError("Password must be 5-30 characters.");
                check = false;
            }

            if (!password.equals(confirm)) {
                userError.setConfirmError("Passwords do not match.");
                check = false;
            }

            if (authService.emailExists(email)) {
                userError.setEmailError("Email already exists.");
                check = false;
            }

            // Process registration
            if (check) {
                // Hash the password before saving
                password = PasswordHasher.hash(password);
                // Create a new user entity
                User user = new User(fullName, email, password);
                // Save user to the database
                userService.addUser(user);
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                request.setAttribute("USER_ERROR", userError);
                request.getRequestDispatcher(REGISTER_PAGE).forward(request, response);
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            log("Database constraint violation at RegisterController: " + e.getMessage(), e);
            userError.setEmailError("Duplicate email in DB.");
            request.setAttribute("USER_ERROR", userError);
            request.getRequestDispatcher(REGISTER_PAGE).forward(request, response);
        } catch (Exception e) {
            log("Unexpected error at RegisterController: " + e.getMessage(), e);
            userError.setError("Unexpected server error.");
            request.setAttribute("USER_ERROR", userError);
            request.getRequestDispatcher(REGISTER_PAGE).forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Register Controller";
    }
}

