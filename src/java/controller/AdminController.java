package controller;

import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import service.*;

import java.io.*;

@WebServlet("/admin")
public class AdminController extends HttpServlet {

    private final UserService userService = new UserService();
    private final BookService bookService = new BookService();
    private final BookRequestService bookRequestService = new BookRequestService();
    private final FineService fineService = new FineService();
    private final SystemConfigService systemConfigService = new SystemConfigService();

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

                    // Lấy số lượng người dùng
                    request.setAttribute("userCount", userService.getUserCount());
                    // Tổng số sách
                    request.setAttribute("totalBooks", bookService.getTotalBooks());
                    // Tổng yêu cầu mượn sách
                    request.setAttribute("pendingRequests", bookRequestService.countPendingRequests());
                    // Tỏng vé phạt chưa thanh toán
                    request.setAttribute("unpaidFines", fineService.countUnpaidFines());
                    // Đầy đủ cấu hình hệ thống
                    request.setAttribute("systemConfigs", systemConfigService.getAllConfigs());
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
