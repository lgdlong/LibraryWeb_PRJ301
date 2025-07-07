package controller;

import dto.*;
import enums.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import service.*;

import java.io.*;
import java.util.*;

@WebServlet("/admin/borrow-records")
public class AdminBorrowRecordController extends HttpServlet {
    private final BorrowRecordService borrowRecordService = new BorrowRecordService();
    private final FineService fineService = new FineService();

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

        String action = request.getParameter("action");

        // Handle manual overdue check action
        if ("check-overdue".equals(action)) {
            handleManualOverdueCheck(request, response);
            return;
        }

        // Existing logic for returning books
        long id = request.getParameter("id") == null ? 0 : Long.parseLong(request.getParameter("id"));
        boolean isPaid = "true".equals(request.getParameter("finePaid"));

        try {
            BorrowRecordDTO record = borrowRecordService.getById(id);
            if (record == null) {
                sendError(request, response, "Borrow record not found");
                return;
            }

            BorrowStatus currentStatus = BorrowStatus.fromString(record.getStatus());
            // Xử lý từng trạng thái thực tế của record
            if (currentStatus == BorrowStatus.OVERDUE) {
                // Trả sách quá hạn, cho phép trả và chọn đã thanh toán phạt hoặc chưa
                borrowRecordService.returnBorrowedRecord(record, isPaid);
                if (isPaid) {
                    setSuccessAndRedirect(request, response, "Overdue record returned and fine marked as paid");
                } else {
                    setSuccessAndRedirect(request, response, "Overdue record returned. Fine remains unpaid.");
                }
                return;
            }

            if (currentStatus == BorrowStatus.BORROWED) {
                // Trả đúng hạn
                borrowRecordService.returnBorrowedRecord(record, false);
                setSuccessAndRedirect(request, response, "Borrowed record returned successfully");
                return;
            }

            // Không phải trạng thái hợp lệ để trả
            sendError(request, response, "This record is not in a returnable state.");
        } catch (Exception e) {
            e.printStackTrace();
            sendError(request, response, "Failed to update borrow record: " + e.getMessage());
        }
    }

    /**
     * Handle manual overdue check request
     */
    private void handleManualOverdueCheck(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        try {
            // Run the enhanced overdue check with tracking
            BorrowRecordService.OverdueProcessingResult result =
                borrowRecordService.checkAndUpdateOverdueWithTracking();

            // Create detailed success message
            StringBuilder message = new StringBuilder();
            message.append("Manual overdue check completed successfully! ");
            message.append("Checked: ").append(result.getTotalChecked()).append(" records, ");
            message.append("Updated to overdue: ").append(result.getUpdatedToOverdue()).append(", ");
            message.append("Fines created: ").append(result.getFinesCreated());

            if (result.getErrorCount() > 0) {
                message.append(", Errors: ").append(result.getErrorCount());
            }

            setSuccessAndRedirect(request, response, message.toString());

        } catch (Exception e) {
            System.err.println("Error during manual overdue check: " + e.getMessage());
            sendError(request, response, "Failed to run overdue check: " + e.getMessage());
        }
    }

    // --- Hỗ trợ: các hàm phụ clean code ---
    private void setSuccessAndRedirect(HttpServletRequest request, HttpServletResponse response, String message) throws IOException {
        request.getSession().setAttribute("successMessage", message); // flash message
        response.sendRedirect(request.getContextPath() + "/admin/borrow-records");
    }

    private void sendError(HttpServletRequest request, HttpServletResponse response, String errorMsg) throws IOException {
        request.getSession().setAttribute("errorMessage", errorMsg); // flash message
        response.sendRedirect(request.getContextPath() + "/admin/borrow-records");
    }

}
