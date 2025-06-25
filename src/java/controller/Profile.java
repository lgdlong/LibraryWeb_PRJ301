// File: Profile.java
package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.*;

public class Profile extends HttpServlet {

    private boolean isUserNotAuthenticated(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return true;
        Object loginUser = session.getAttribute("LOGIN_USER");
        return !(loginUser instanceof entity.User);
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
