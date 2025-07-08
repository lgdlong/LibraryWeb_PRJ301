package controller;

import jakarta.servlet.*;
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
    private final BorrowRecordService borrowRecordService = new BorrowRecordService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // **GỌI kiểm tra & cập nhật overdue trước khi lấy danh sách**
            borrowRecordService.checkAndUpdateOverdue();

            // **Xử lý các khoản phạt quá hạn**
            fineService.processOverdueFines();

            request.setAttribute("pageTitle", "Admin Dashboard");
            request.setAttribute("contentPage", "/admin/admin.jsp");

            request.setAttribute("userCount", userService.getUserCount());
            request.setAttribute("totalBooks", bookService.getTotalBooks());
            request.setAttribute("pendingRequests", bookRequestService.countPendingRequests());
            request.setAttribute("unpaidFines", fineService.countUnpaidFines());
            request.setAttribute("borrowedCount", borrowRecordService.countCurrentlyBorrowedBooks());
            request.setAttribute("mostBorrowedBooks", borrowRecordService.getMostBorrowedBooks());

            // Add new statistics
            request.setAttribute("monthlyBorrowingStats", borrowRecordService.getMonthlyBorrowingStats());
            request.setAttribute("averageBorrowDuration", borrowRecordService.getAverageBorrowDuration());

            request.getRequestDispatcher("/admin/layout.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal error in Dashboard.");
        }
    }
}
