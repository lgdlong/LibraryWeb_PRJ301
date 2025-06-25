// File: Profile.java
package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.*;

public class Profile extends HttpServlet {

    private boolean isUserNotAuthenticated(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session == null || session.getAttribute("LOGIN_USER") == null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        if (isUserNotAuthenticated(request)) {
            response.sendRedirect(request.getContextPath() + "/GuestHomeController");
            return;
        }
        request.getRequestDispatcher("/view/view-profile.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        if (isUserNotAuthenticated(request)) {
            response.sendRedirect(request.getContextPath() + "/GuestHomeController");
            return;
        }
        // Handle profile update logic here
        // Then forward to the JSP page
        request.getRequestDispatcher("/view/view-profile.jsp").forward(request, response);
    }

}
