package controller;

import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;

@WebServlet("/admin")
public class AdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            String action = request.getParameter("action");
            String pageTitle;
            String contentPage;

            switch (action != null ? action : "") {
                case "config":
                    pageTitle = "Config System Variable";
                    contentPage = "/admin/config.jsp";
                    break;
                case "users":
                    pageTitle = "Manage Users";
                    contentPage = "/admin/userList.jsp";
                    break;
                default:
                    pageTitle = "Admin Dashboard";
                    contentPage = "/admin/admin.jsp";
            }

            request.setAttribute("pageTitle", pageTitle);
            request.setAttribute("contentPage", contentPage);
            request.getRequestDispatcher("/admin/layout.jsp").forward(request, response);

        } catch (Exception e) {
            // Ghi log (có thể dùng logging framework nếu muốn)
            e.printStackTrace();

            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal error in AdminController.");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        // TODO: Xử lý POST nếu cần
    }

    @Override
    public String getServletInfo() {
        return "AdminController handles admin dashboard and routing.";
    }
}
