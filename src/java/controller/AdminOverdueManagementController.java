package controller;

import dto.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import service.*;

import java.io.*;
import java.util.*;

@WebServlet("/admin/overdue-management")
public class AdminOverdueManagementController extends HttpServlet {
    private final BorrowRecordService borrowRecordService = new BorrowRecordService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        try {
            // Get overdue statistics
            Map<String, Object> stats = borrowRecordService.getOverdueStatistics();
            request.setAttribute("overdueStats", stats);
            
            // Get records approaching due date (next 3 days)
            List<BorrowRecordDTO> approachingDue = borrowRecordService.getRecordsApproachingDueDate(3);
            request.setAttribute("approachingDueRecords", approachingDue);
            
            request.setAttribute("contentPage", "/admin/overdue-management.jsp");
            request.setAttribute("pageTitle", "Overdue Management");
            
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Failed to load overdue management: " + e.getMessage());
        } finally {
            request.getRequestDispatcher("/admin/layout.jsp").forward(request, response);
        }
    }
}
