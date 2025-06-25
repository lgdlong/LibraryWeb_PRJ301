package controller;

import dto.*;
import enums.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import service.*;

import java.io.*;
import java.util.*;

@WebServlet("/admin/fines")
public class AdminFineController extends HttpServlet {

    FineService fineService = new FineService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        List<FineDTO> allFines = fineService.getAllFines();
        request.setAttribute("fineList", allFines);
        request.setAttribute("pageTitle", "Book Request Management");
        request.setAttribute("contentPage", "/admin/fine-management.jsp");
        request.getRequestDispatcher("/admin/layout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String idStr = request.getParameter("id");
        String newStatusStr = request.getParameter("paidStatus");

        if (idStr != null && newStatusStr != null) {
            try {
                long id = Long.parseLong(idStr);
                PaidStatus newStatusE = PaidStatus.fromString(newStatusStr);
                String newStatus = (newStatusE != null) ? newStatusE.toString() : null;

                FineDTO fine = fineService.getFineById(id);
                if (fine != null) {
                    fine.setPaidStatus(newStatus);
                    fineService.updateFine(fine);
                }
            } catch (Exception e) {
                e.printStackTrace(); // Có thể log hoặc redirect error page
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin/fines");
    }

}
