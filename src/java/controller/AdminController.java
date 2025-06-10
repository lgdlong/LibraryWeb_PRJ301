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
            request.setAttribute("pageTitle", "Admin Dashboard");
            request.setAttribute("contentPage", "/admin/admin.jsp");

            request.setAttribute("userCount", userService.getUserCount());
            request.setAttribute("totalBooks", bookService.getTotalBooks());
            request.setAttribute("pendingRequests", bookRequestService.countPendingRequests());
            request.setAttribute("unpaidFines", fineService.countUnpaidFines());
            request.setAttribute("borrowedCount", borrowRecordService.countCurrentlyBorrowedBooks());
            request.setAttribute("mostBorrowedBooks", borrowRecordService.getMostBorrowedBooks());

            request.getRequestDispatcher("/admin/layout.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal error in Dashboard.");
        }
    }

    @Override
    public String getServletInfo() {
        return "AdminController (Dashboard only)";
    }
}
