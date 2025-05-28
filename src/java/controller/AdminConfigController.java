package controller;

import dto.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import service.*;

import java.io.*;

@WebServlet("/admin/config")
public class AdminConfigController extends HttpServlet {

    private final SystemConfigService configService = new SystemConfigService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("systemConfigs", configService.getAllConfigs());
        req.setAttribute("pageTitle", "System Config");
        req.setAttribute("contentPage", "/admin/system-config.jsp");
        req.getRequestDispatcher("/admin/layout.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");

        try {
            if ("delete".equalsIgnoreCase(action)) {
                handleDelete(req);
            } else {
                handleAddOrUpdate(req);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/config");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid config request");
        }
    }

    private void handleDelete(HttpServletRequest req) {
        long id = Long.parseLong(req.getParameter("id"));
        configService.delete(id);
    }

    private void handleAddOrUpdate(HttpServletRequest req) {
        long id = 0;
        String idStr = req.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            id = Long.parseLong(idStr);
        }

        String key = req.getParameter("configKey");
        String value = req.getParameter("configValue");
        String desc = req.getParameter("description");

        SystemConfigDb config = new SystemConfigDb(id, key, value, desc);

        if (id == 0) {
            configService.add(config);
        } else {
            configService.update(config);
        }
    }
}
