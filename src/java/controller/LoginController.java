/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.UserDao;

import entity.User;
import enums.UserRole;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.SQLException;


/**
 *
 * @author nguye
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {

    private static final String US = "user";
    private static final String AD = "admin"; 
    private static final String ADMIN_PAGE = "/admin/layout.jsp";
    private static final String USER_PAGE = "/user.jsp";
    private static final String ERROR = "Login.jsp";

    

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

        UserDao dao = new UserDao();
        User us = dao.checkLogin(email, password);

        if (us != null) {
            HttpSession session = request.getSession();
            session.setAttribute("LOGIN_USER", us);
            UserRole role = us.getRole();

            if (role.equals(UserRole.ADMIN)) {
                response.sendRedirect(request.getContextPath() + "/admin");
            } else {
                response.sendRedirect(request.getContextPath() + "/user.jsp"); // ✅ Thêm dấu /
            }
        } else {
            request.setAttribute("ERROR", "Incorrect email or password");
            request.getRequestDispatcher("/Login.jsp").forward(request, response); // ✅ Hiển thị lại trang login
        }
    } catch (SQLException e) {
        log("ERROR at LoginController: " + e.toString());
        request.setAttribute("ERROR", "Internal server error");
        request.getRequestDispatcher("/Login.jsp").forward(request, response); // ✅ Xử lý fallback khi lỗi
    }
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
