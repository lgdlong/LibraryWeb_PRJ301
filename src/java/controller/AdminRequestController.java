package controller;

import dto.*;
import entity.*;
import enums.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import service.*;

import java.io.*;
import java.util.*;

@WebServlet("/admin/requests")
public class AdminRequestController extends HttpServlet {

    private final BookRequestService requestService = new BookRequestService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Kiểm tra quyền admin
//        HttpSession session = req.getSession();
//        User currentUser = (User) session.getAttribute("currentUser");
//        if (currentUser == null || !"ADMIN".equals(currentUser.getRole().toString())) {
//            resp.sendRedirect(req.getContextPath() + "/login");
//            return;
//        }
//
//        // Thiết lập CSRF token nếu chưa có
//        if (session.getAttribute("csrf_token") == null) {
//            session.setAttribute("csrf_token", UUID.randomUUID().toString());
//        }

        // Lấy tất cả yêu cầu mà không lọc theo trạng thái
        List<BookRequestDTO> requests = requestService.getAllRequests();

        req.setAttribute("requestList", requests);
        req.setAttribute("pageTitle", "Book Request Management");
        req.setAttribute("contentPage", "/admin/request-management.jsp");
        req.getRequestDispatcher("/admin/layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Kiểm tra quyền admin
//        HttpSession session = req.getSession();
//        User currentUser = (User) session.getAttribute("currentUser");
//        if (currentUser == null || !"ADMIN".equals(currentUser.getRole().toString())) {
//            resp.sendRedirect(req.getContextPath() + "/login");
//            return;
//        }
//
//        // Kiểm tra CSRF token
//        String csrfToken = req.getParameter("csrf_token");
//        if (csrfToken == null || !csrfToken.equals(session.getAttribute("csrf_token"))) {
//            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid CSRF token.");
//            return;
//        }

        String idParam = req.getParameter("id");
        String statusParam = req.getParameter("status");

        try {
            long id = Long.parseLong(idParam);
            RequestStatus newStatus = RequestStatus.valueOf(statusParam.toUpperCase());

            // Kiểm tra trạng thái hợp lệ (chỉ cho phép APPROVED hoặc REJECTED)
            if (newStatus != RequestStatus.APPROVED && newStatus != RequestStatus.REJECTED) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid status: Must be APPROVED or REJECTED.");
                return;
            }

            BookRequest request = requestService.getRequestById(id);
            if (request == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Request not found.");
                return;
            }

            // Kiểm tra trạng thái hiện tại là PENDING
            if (request.getStatus() != RequestStatus.PENDING) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Request is not in PENDING status.");
                return;
            }

            // Cập nhật trạng thái
            requestService.updateStatus(id, newStatus.toString());
            resp.sendRedirect(req.getContextPath() + "/admin/requests");

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request ID.");
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid status value: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating request status.");
        }
    }

    @Override
    public String getServletInfo() {
        return "Admin Request Controller for managing book requests";
    }
}
