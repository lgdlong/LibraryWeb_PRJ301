package controller;

import entity.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import service.*;

import java.io.*;
import java.util.*;

@WebServlet("/admin/users")
public class AdminUserController extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "view":
                viewUserById(request, response);
                break;
            case "list":
            default:
                listUsers(request, response);
                break;
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        List<User> users = userService.getAllUsers();

        request.setAttribute("users", users);
        request.setAttribute("pageTitle", "Manage Users");
        request.setAttribute("contentPage", "/admin/userList.jsp");

        request.getRequestDispatcher("/admin/layout.jsp").forward(request, response);
    }

    private void viewUserById(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String idStr = request.getParameter("id");

        if (idStr == null || idStr.isEmpty() || !idStr.matches("\\d+")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required.");
            return;
        }

        try {
            long id = Long.parseLong(idStr);
            User user = userService.getUserById(id);

            if (user == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
                return;
            }

            request.setAttribute("user", user);
            request.setAttribute("pageTitle", "User Detail");
            request.setAttribute("contentPage", "/admin/userDetail.jsp");

            request.getRequestDispatcher("/admin/layout.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID.");
        }
    }

    // Optional: handle create/update/delete via doPost()
}
