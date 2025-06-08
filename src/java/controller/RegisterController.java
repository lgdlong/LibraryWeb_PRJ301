/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.UserDao;
import dto.UserDTO;
import dto.UserError;
import entity.User;
import enums.UserRole;
import enums.UserStatus;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.AuthService;
import service.UserService;

/**
 *
 * @author nguye
 */
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
                userError.setFullnameError("Fullname must be 5-30 characters.");
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
                User user = new User(fullName, email, password);
                userService.addUser(user);
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                request.setAttribute("USER_ERROR", userError);
                request.getRequestDispatcher(REGISTER_PAGE).forward(request, response);
            }

        } catch (Exception e) {
            log("Error at RegisterController: " + e.getMessage(), e);
            if (e.toString().contains("duplicate")) {
                userError.setEmailError("Duplicate email in DB.");
            } else {
                userError.setError("Unexpected server error.");
            }
            request.setAttribute("USER_ERROR", userError);
            request.getRequestDispatcher(REGISTER_PAGE).forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Register Controller";
    }
}

