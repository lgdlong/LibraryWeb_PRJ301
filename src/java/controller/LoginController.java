package controller;

import db.*;
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

        // **TEMPORARY: Hash all plain text passwords - REMOVE AFTER RUNNING ONCE**
        try {
            hashAllPlainTextPasswords();
        } catch (Exception e) {
            System.err.println("Error hashing passwords: " + e.getMessage());
        }
        // **END TEMPORARY CODE**

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
                    response.sendRedirect(request.getContextPath() + "/GuestHomeController");
                }
            } else {
                request.setAttribute("ERROR", "Incorrect email or password");
                request.getRequestDispatcher("/Login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            System.err.println("ERROR at LoginController: " + e.toString());
            request.setAttribute("ERROR", "Internal server error");
            request.getRequestDispatcher("/Login.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * **TEMPORARY METHOD: Hash all plain text passwords in database**
     * This method will check all users and hash any passwords that aren't already hashed.
     * Should be REMOVED after running once to migrate existing passwords.
     */
    private void hashAllPlainTextPasswords() throws SQLException {
        Connection cn = null;
        PreparedStatement selectStmt = null;
        PreparedStatement updateStmt = null;
        ResultSet rs = null;
        
        final String SELECT_ALL_USERS = "SELECT [id], [email], [password] FROM users";
        final String UPDATE_PASSWORD = "UPDATE users SET [password] = ? WHERE [id] = ?";
        
        try {
            cn = DbConfig.getConnection();
            if (cn != null) {
                System.out.println("=== Starting password hashing migration ===");
                
                selectStmt = cn.prepareStatement(SELECT_ALL_USERS);
                rs = selectStmt.executeQuery();
                
                updateStmt = cn.prepareStatement(UPDATE_PASSWORD);
                
                int totalUsers = 0;
                int hashedCount = 0;
                
                while (rs.next()) {
                    totalUsers++;
                    long userId = rs.getLong("id");
                    String email = rs.getString("email");
                    String currentPassword = rs.getString("password");
                    
                    // Check if password is already hashed (hashed passwords are typically longer and contain special chars)
                    // Simple heuristic: if password length < 20 or doesn't contain '$', it's probably plain text
                    boolean isPlainText = currentPassword.length() < 20 || !currentPassword.contains("$");
                    
                    if (isPlainText) {
                        // Hash the plain text password
                        String hashedPassword = security.PasswordHasher.hash(currentPassword);
                        
                        // Update in database
                        updateStmt.setString(1, hashedPassword);
                        updateStmt.setLong(2, userId);
                        updateStmt.executeUpdate();
                        
                        hashedCount++;
                        System.out.println("Hashed password for user: " + email + " (ID: " + userId + ")");
                    } else {
                        System.out.println("Password already hashed for user: " + email + " (ID: " + userId + ")");
                    }
                }
                
                System.out.println("=== Password hashing migration completed ===");
                System.out.println("Total users: " + totalUsers);
                System.out.println("Passwords hashed: " + hashedCount);
                System.out.println("Already hashed: " + (totalUsers - hashedCount));
                System.out.println("*** REMEMBER TO REMOVE THIS METHOD FROM LoginController.doGet() ***");
            }
        } catch (Exception e) {
            System.err.println("Error during password hashing migration: " + e.getMessage());
            e.printStackTrace();
            throw new SQLException("Password hashing migration failed", e);
        } finally {
            if (rs != null) rs.close();
            if (selectStmt != null) selectStmt.close();
            if (updateStmt != null) updateStmt.close();
            if (cn != null) cn.close();
        }
    }
}
