package controller;

import dto.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import service.*;

import java.io.*;
import java.util.*;

@WebServlet("/admin/borrow-records")
public class AdminBorrowRecordController extends HttpServlet {
    private final BorrowRecordService borrowRecordService = new BorrowRecordService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
//        // Kiểm tra quyền admin
//        HttpSession session = request.getSession();
//        User currentUser = (User) session.getAttribute("currentUser");
//        if (currentUser == null || !"ADMIN".equals(currentUser.getRole().toString())) {
//            response.sendRedirect(request.getContextPath() + "/login");
//            return;
//        }
//
//        // Thiết lập CSRF token nếu chưa có
//        if (session.getAttribute("csrf_token") == null) {
//            session.setAttribute("csrf_token", UUID.randomUUID().toString());
//        }

        // Lấy danh sách bản ghi mượn
        List<BorrowRecordDTO> dtos = borrowRecordService.getAll();

        // Thiết lập thuộc tính cho JSP
        request.setAttribute("borrowRecordList", dtos);
        request.setAttribute("pageTitle", "Borrow Record Management");
        request.setAttribute("contentPage", "/admin/borrow-record-management.jsp");

        // Chuyển tiếp đến layout
        request.getRequestDispatcher("/admin/layout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // Hiện tại không xử lý POST vì yêu cầu chỉ cần hiển thị danh sách
        response.sendRedirect(request.getContextPath() + "/admin/borrow-records");
    }

    @Override
    public String getServletInfo() {
        return "Admin Borrow Record Controller for managing borrow records";
    }
}
