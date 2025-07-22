package controller;

import dto.*;
import entity.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


@WebServlet("/borrow/history")
public class ViewHistoryBorrowController extends HttpServlet {

    private final BorrowRecordService borrowRecordService = new BorrowRecordService();
    private final FineService fineService = new FineService();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("LOGIN_USER");




            borrowRecordService.checkAndUpdateOverdue();


            fineService.processOverdueFines(user.getId());

            List<BorrowRecordDTO> history = borrowRecordService.getBorrowHistoryByUserId(user.getId());

            Map<Long, FineDTO> fineMap = new HashMap<>();
            for (BorrowRecordDTO record : history) {
                FineDTO fine = fineService.getFineByBorrowRecordId(record.getId());
                if (fine != null) {
                    fineMap.put(record.getId(), fine);
                }
            }


            List<BorrowRecordDTO> overdueSortedByFine = new ArrayList<>();
            for (BorrowRecordDTO record : history) {
                FineDTO fine = fineMap.get(record.getId());
                if (fine != null
                    && record.getReturnDate() == null
                    && record.getDueDate() != null
                    && record.getDueDate().isBefore(java.time.LocalDate.now())) {
                    overdueSortedByFine.add(record);
                }
            }

            overdueSortedByFine.sort((r1, r2) -> {
                Double f1 = fineMap.get(r1.getId()).getFineAmount();
                Double f2 = fineMap.get(r2.getId()).getFineAmount();
                return f2.compareTo(f1); // sắp xếp giảm dần
            });


            request.setAttribute("borrowHistory", history);
            request.setAttribute("fineMap", fineMap);


            request.setAttribute("overdueSortedByFine", overdueSortedByFine);

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
