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
        String statusParam = req.getParameter("status");
        if (statusParam == null) {
            statusParam = UserStatus.ACTIVE.toString();
        }

        List<User> userEntities = userService.getUsersByStatus(statusParam);

        if (search != null && !search.trim().isEmpty()) {
            String keyword = search.trim().toLowerCase();
            userEntities = userEntities.stream()
                .filter(u -> u.getEmail().toLowerCase().contains(keyword)
                    || u.getName().toLowerCase().contains(keyword))
                .collect(Collectors.toList());
        }

        List<UserDTO> users = userEntities.stream()
            .map(UserMapping::toUserDTO)
            .collect(Collectors.toList());

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
                long idToDelete = parseLongOrZero(deleteId);
                if (idToDelete > 0) {
                    userService.deleteUser(idToDelete);
                } else {
                    throw new IllegalArgumentException("Invalid user ID for deletion");
                }
            } else {
                long id = parseLongOrZero(req.getParameter("id"));
                String name = req.getParameter("name");
                String email = req.getParameter("email");
                String password = req.getParameter("password");
                String roleStr = req.getParameter("role");
                String statusStr = req.getParameter("status");

                // Validate role parameter
                UserRole role;
                try {
                    role = UserRole.fromString(roleStr);
                } catch (IllegalArgumentException | NullPointerException e) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid user role: " + roleStr);
                    return;
                }
                // Validate status parameter
                UserStatus status;
                try {
                    status = UserStatus.fromString(statusStr);
                } catch (IllegalArgumentException | NullPointerException e) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid user status: " + statusStr);
                    return;
                }

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
                        User existingUser = userService.getUserById(id);
                        if (existingUser == null) {
                            throw new IllegalArgumentException("User not found with ID: " + id);
                        }
                        password = existingUser.getPassword();
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
