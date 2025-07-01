package controller;

import dto.*;
import entity.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import service.*;

import java.io.*;
import java.util.*;


public class ViewHistoryBorrowController extends HttpServlet {

    private final BorrowRecordService borrowRecordService = new BorrowRecordService();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // **GỌI kiểm tra & cập nhật overdue trước khi lấy danh sách**
        borrowRecordService.checkAndUpdateOverdue();

        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("LOGIN_USER");

            if (user == null) {
                response.sendRedirect("Login.jsp");
                return;
            }

            BorrowRecordService borrowRecordService = new BorrowRecordService();
            List<BorrowRecordDTO> history = borrowRecordService.getBorrowHistoryByUserId(user.getId());


            request.setAttribute("borrowHistory", history);
            request.setAttribute("contentPage", "/user/history-borrow.jsp");
            request.setAttribute("sidebarPage", "/user/my-library-sidebar.jsp");
            request.getRequestDispatcher("/guest/layout.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);
    }
}
