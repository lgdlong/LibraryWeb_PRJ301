/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dto.*;
import entity.*;
import enums.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import mapper.*;
import service.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

@WebServlet("/admin/users")
public class AdminUserController extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter("search");
        List<UserDTO> users = new ArrayList<>();

        if (search != null && !search.trim().isEmpty()) {
            User user = userService.searchByEmail(search.trim());
            if (user != null) {
                users.add(UserMapping.toUserDTO(user));
            } else {
                req.setAttribute("errorMessage", "No user found with email: " + search);
            }
        } else {
            users = userService.getAllUsers().stream()
                .map(UserMapping::toUserDTO)
                .collect(Collectors.toList());
        }

        req.setAttribute("userList", users);
        req.setAttribute("pageTitle", "User Management");
        req.setAttribute("contentPage", "/admin/user-management.jsp");
        req.getRequestDispatcher("/admin/layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String deleteId = req.getParameter("delete");

        try {
            if (deleteId != null) {
                userService.deleteUser(Long.parseLong(deleteId));
            } else {
                long id = parseLongOrZero(req.getParameter("id"));
                String name = req.getParameter("name");
                String email = req.getParameter("email");
                String password = req.getParameter("password");
                String roleStr = req.getParameter("role");
                String statusStr = req.getParameter("status");

                UserRole role = UserRole.fromString(roleStr);
                UserStatus status = UserStatus.fromString(statusStr);

                if (id == 0) {
                    // THÊM MỚI
                    if (password == null || password.trim().isEmpty()) {
                        throw new IllegalArgumentException("Password is required for new users.");
                    }

                    User user = new User(name, email, password, role, status);
                    userService.addUser(user);
                } else {
                    // CẬP NHẬT
                    if (password == null || password.trim().isEmpty()) {
                        // If password is not provided, we assume it's an update without changing the password
                        password = userService.getUserById(id).getPassword();
                    }

                    User user = new User(id, name, email, password, role, status);
                    userService.updateUser(user);
                }
            }

            resp.sendRedirect(req.getContextPath() + "/admin/users");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user operation");
        }
    }

    private long parseLongOrZero(String val) {
        try {
            return (val != null && !val.isEmpty()) ? Long.parseLong(val) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
