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
    private final FineService fineService = new FineService();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("LOGIN_USER");

            if (user == null) {
                response.sendRedirect("Login.jsp");
                return;
            }

            // **GỌI kiểm tra & cập nhật overdue trước khi lấy danh sách**
            borrowRecordService.checkAndUpdateOverdue();

            // **Xử lý các khoản phạt quá hạn chỉ cho user hiện tại (tối ưu hiệu suất)**
            fineService.processOverdueFines(user.getId());

            List<BorrowRecordDTO> history = borrowRecordService.getBorrowHistoryByUserId(user.getId());

            Map<Long, FineDTO> fineMap = new HashMap<>();
            for (BorrowRecordDTO record : history) {
                FineDTO fine = fineService.getFineByBorrowRecordId(record.getId());
                if (fine != null) {
                    fineMap.put(record.getId(), fine);
                }
            }


            request.setAttribute("borrowHistory", history);
            request.setAttribute("fineMap", fineMap);
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
