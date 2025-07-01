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

        try {

            // Lấy danh sách bản ghi mượn (sau khi update)
            List<BorrowRecordDTO> dtos = borrowRecordService.getAll();

            // Thiết lập thuộc tính cho JSP
            request.setAttribute("borrowRecordList", dtos);
            request.setAttribute("contentPage", "/admin/borrow-record-management.jsp");

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Unable to retrieve borrow records");
            request.setAttribute("borrowRecordList", Collections.emptyList());
        } finally {
            request.setAttribute("pageTitle", "Borrow Record Management");
            // Luôn forward đến layout
            request.getRequestDispatcher("/admin/layout.jsp").forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // Hiện tại không xử lý POST vì yêu cầu chỉ cần hiển thị danh sách
        response.sendRedirect(request.getContextPath() + "/admin/borrow-records");
    }
}
