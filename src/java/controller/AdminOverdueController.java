package controller;

import dto.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import service.*;

import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

/**
 * Administrative controller for manually triggering overdue checks and viewing statistics.
 * This provides admin users with manual control over the overdue checking process.
 */
@WebServlet("/admin/overdue-check")
public class AdminOverdueController extends HttpServlet {

    private final BorrowRecordService borrowRecordService = new BorrowRecordService();
    private final FineService fineService = new FineService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check admin access (reuse existing authorization logic)
        // You may want to add proper admin authentication check here

        String action = request.getParameter("action");

        if ("run".equals(action)) {
            // Manually trigger overdue check
            runOverdueCheck(request, response);
        } else if ("stats".equals(action)) {
            // Show overdue statistics
            showOverdueStatistics(request, response);
        } else {
            // Show the admin overdue management page
            showOverdueManagementPage(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Manually trigger an overdue check and show results
     */
    private void runOverdueCheck(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            LocalDateTime startTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<title>Manual Overdue Check - Library Admin</title>");
            out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
            out.println("</head><body>");
            out.println("<div class='container mt-4'>");
            out.println("<h2>Manual Overdue Check Results</h2>");
            out.println("<div class='alert alert-info'>Check started at: " + startTime.format(formatter) + "</div>");

            // Run the enhanced overdue check
            BorrowRecordService.OverdueProcessingResult result =
                    borrowRecordService.checkAndUpdateOverdueWithTracking();

            // Show results
            out.println("<div class='card'>");
            out.println("<div class='card-header'><strong>Processing Results</strong></div>");
            out.println("<div class='card-body'>");
            out.println("<div class='row'>");
            out.println("<div class='col-md-3'><div class='text-center'>");
            out.println("<h4 class='text-primary'>" + result.getTotalChecked() + "</h4>");
            out.println("<small class='text-muted'>Records Checked</small></div></div>");

            out.println("<div class='col-md-3'><div class='text-center'>");
            out.println("<h4 class='text-warning'>" + result.getUpdatedToOverdue() + "</h4>");
            out.println("<small class='text-muted'>Updated to Overdue</small></div></div>");

            out.println("<div class='col-md-3'><div class='text-center'>");
            out.println("<h4 class='text-success'>" + result.getFinesCreated() + "</h4>");
            out.println("<small class='text-muted'>Fines Created</small></div></div>");

            out.println("<div class='col-md-3'><div class='text-center'>");
            out.println("<h4 class='text-danger'>" + result.getErrorCount() + "</h4>");
            out.println("<small class='text-muted'>Errors</small></div></div>");
            out.println("</div>");

            if (result.getErrorCount() > 0) {
                out.println("<div class='mt-3'>");
                out.println("<h6>Errors encountered:</h6>");
                out.println("<ul class='list-group'>");
                for (String error : result.getErrors()) {
                    out.println("<li class='list-group-item list-group-item-danger'>" + error + "</li>");
                }
                out.println("</ul></div>");
            }
            out.println("</div></div>");

            // Process overdue fines
            out.println("<div class='alert alert-info mt-3'>Processing overdue fines...</div>");
            fineService.processOverdueFines();
            out.println("<div class='alert alert-success'>Fine processing completed!</div>");

            LocalDateTime endTime = LocalDateTime.now();
            long durationSeconds = java.time.Duration.between(startTime, endTime).getSeconds();
            out.println("<div class='alert alert-secondary'>Total processing time: " + durationSeconds + " seconds</div>");

            out.println("<div class='mt-4'>");
            out.println("<a href='" + request.getContextPath() + "/admin/overdue-check' class='btn btn-secondary'>Back to Overdue Management</a>");
            out.println("<a href='" + request.getContextPath() + "/admin/borrow-records' class='btn btn-primary ms-2'>View Borrow Records</a>");
            out.println("</div>");

            out.println("</div></body></html>");

        } catch (Exception e) {
            out.println("<div class='alert alert-danger'>Error during overdue check: " + e.getMessage() + "</div>");
            out.println("<a href='" + request.getContextPath() + "/admin/overdue-check' class='btn btn-secondary'>Back</a>");
            out.println("</div></body></html>");
        }
    }

    /**
     * Show overdue statistics
     */
    private void showOverdueStatistics(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Map<String, Object> stats = borrowRecordService.getOverdueStatistics();
            request.setAttribute("overdueStats", stats);
            request.setAttribute("contentPage", "/admin/overdue-statistics.jsp");
            request.setAttribute("pageTitle", "Overdue Statistics");
            request.getRequestDispatcher("/admin/layout.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Failed to load overdue statistics: " + e.getMessage());
            request.getRequestDispatcher("/admin/layout.jsp").forward(request, response);
        }
    }

    /**
     * Show the main overdue management page
     */
    private void showOverdueManagementPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Get some basic statistics for the management page
            Map<String, Object> stats = borrowRecordService.getOverdueStatistics();
            request.setAttribute("overdueStats", stats);

            // Get records approaching due date (next 3 days)
            List<BorrowRecordDTO> approachingDue = borrowRecordService.getRecordsApproachingDueDate(3);
            request.setAttribute("approachingDueRecords", approachingDue);

            request.setAttribute("contentPage", "/admin/overdue-management.jsp");
            request.setAttribute("pageTitle", "Overdue Management");
            request.getRequestDispatcher("/admin/layout.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Failed to load overdue management: " + e.getMessage());
            request.getRequestDispatcher("/admin/layout.jsp").forward(request, response);
        }
    }
}
