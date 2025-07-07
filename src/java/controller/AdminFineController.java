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
        
        String action = request.getParameter("action");
        
        // Handle manual overdue fine processing
        if ("process-overdue".equals(action)) {
            handleManualFineProcessing(request, response);
            return;
        }
        
        // Existing logic for updating fine status
        String idStr = request.getParameter("id");
        String newStatusStr = request.getParameter("paidStatus");

        if (idStr != null && newStatusStr != null) {
            try {
                long id = Long.parseLong(idStr);
                PaidStatus newStatus = PaidStatus.fromString(newStatusStr);
                if (newStatus == null) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Invalid status provided");
                    return;
                }

                FineDTO fine = fineService.getFineById(id);
                if (fine != null) {
                    fine.setPaidStatus(newStatus.toString());
                    fineService.updateFine(fine);
                }
            } catch (Exception e) {
                System.err.println("Error updating fine status: " + e.getMessage());
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin/fines");
    }

    /**
     * Handle manual overdue fine processing
     */
    private void handleManualFineProcessing(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        try {
            // Process all overdue fines to update amounts
            fineService.processOverdueFines();
            
            // Set success message
            request.getSession().setAttribute("successMessage", 
                "Overdue fine processing completed successfully! All fine amounts have been updated.");
            
        } catch (Exception e) {
            System.err.println("Error during manual fine processing: " + e.getMessage());
            request.getSession().setAttribute("errorMessage", 
                "Failed to process overdue fines: " + e.getMessage());
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/fines");
    }
}
