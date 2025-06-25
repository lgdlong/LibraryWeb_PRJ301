package filter;

import entity.*;
import enums.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;

@WebFilter("/admin/*")
public class AdminAuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        // Dùng đúng key "LOGIN_USER"
        User currentUser = (session != null) ? (User) session.getAttribute("LOGIN_USER") : null;

        if (currentUser == null) {
            System.out.println("[ADMIN_AUTH] Blocked access to " + req.getRequestURI() + ": Not logged in.");
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        if (currentUser.getRole() != UserRole.ADMIN) {
            System.out.println("[ADMIN_AUTH] Blocked access to " + req.getRequestURI()
                + ": User " + currentUser.getEmail() + " (role: " + currentUser.getRole() + ") is not admin.");
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }
}

