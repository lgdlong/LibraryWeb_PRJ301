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
    private final BookService bookService = new BookService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Lấy tất cả yêu cầu mà không lọc theo trạng thái
        List<BookRequestDTO> requests = requestService.getAllRequests();

        req.setAttribute("requestList", requests);
        req.setAttribute("pageTitle", "Book Request Management");
        req.setAttribute("contentPage", "/admin/request-management.jsp");
        req.getRequestDispatcher("/admin/layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        String statusParam = req.getParameter("status");

        try {
            long id = Long.parseLong(idParam);
            RequestStatus newStatus = RequestStatus.valueOf(statusParam.toUpperCase());

            // Chỉ cho phép duyệt hoặc từ chối
            if (newStatus != RequestStatus.APPROVED && newStatus != RequestStatus.REJECTED) {
                setErrorAndForward(req, resp, "Invalid status: Must be APPROVED or REJECTED.");
                return;
            }

            BookRequest request = requestService.getRequestById(id);
            if (request == null) {
                setErrorAndForward(req, resp, "Request not found.");
                return;
            }

            if (request.getStatus() != RequestStatus.PENDING) {
                setErrorAndForward(req, resp, "Request is not in PENDING status.");
                return;
            }

            if (newStatus == RequestStatus.APPROVED) {
                // Nếu duyệt yêu cầu, kiểm tra sách còn không
                if (!bookService.isBookAvailable(request.getBookId())) {
                    setErrorAndForward(req, resp, "Book is not available for request.");
                    return;
                }
                // Cập nhật trạng thái yêu cầu và sách
                requestService.approveRequest(id);
            }

            // Cập nhật trạng thái
            requestService.updateStatus(id, newStatus.toString());
            resp.sendRedirect(req.getContextPath() + "/admin/requests");

        } catch (NumberFormatException e) {
            setErrorAndForward(req, resp, "Invalid request ID.");
        } catch (IllegalArgumentException e) {
            setErrorAndForward(req, resp, "Invalid status value: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            setErrorAndForward(req, resp, "Error updating request status.");
        }
    }

    private void setErrorAndForward(HttpServletRequest req, HttpServletResponse resp, String errorMessage)
        throws ServletException, IOException {
        // Lấy lại danh sách request để trả về trang đúng
        List<BookRequestDTO> requests = requestService.getAllRequests();
        req.setAttribute("requestList", requests);
        req.setAttribute("error", errorMessage);
        req.setAttribute("pageTitle", "Book Request Management");
        req.setAttribute("contentPage", "/admin/request-management.jsp");
        req.getRequestDispatcher("/admin/layout.jsp").forward(req, resp);
    }

}
