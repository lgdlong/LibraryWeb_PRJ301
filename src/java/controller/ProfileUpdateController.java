// ProfileUpdateController.java
package controller;

import dto.*;
import entity.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import service.*;
import validator.*;

import java.io.*;

@WebServlet("/profile/update")
public class ProfileUpdateController extends HttpServlet {

    private final UserService userService = new UserService();
    private final AuthService authService = new AuthService();
    private final ProfileValidator profileValidator = new ProfileValidator(authService);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        User currentUser = getCurrentUser(request);
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/GuestHomeController");
            return;
        }
        request.setAttribute("USER", currentUser); // If you want to prefill the form in JSP
        request.getRequestDispatcher("/profile/edit-profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        User currentUser = getCurrentUser(request);
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/GuestHomeController");
            return;
        }

        try {
            String name = request.getParameter("name");
            String email = request.getParameter("email");

            ProfileUpdateDTO profileDto = new ProfileUpdateDTO(currentUser.getId(), name, email);

            UserError userError = profileValidator.validateProfileUpdate(profileDto, currentUser);

            boolean hasError = (userError.getError() != null && !userError.getError().isEmpty())
                || (userError.getNameError() != null && !userError.getNameError().isEmpty())
                || (userError.getEmailError() != null && !userError.getEmailError().isEmpty());

            if (hasError) {
                request.setAttribute("USER_ERROR", userError);
                request.setAttribute("USER", profileDto);
                request.getRequestDispatcher("/profile/edit-profile.jsp").forward(request, response);
                return;
            }

            try {
                userService.updateUserProfile(profileDto, currentUser, request.getSession());
            } catch (Exception ex) {
                System.err.println("Exception updating session user: " + ex.getMessage());
                ex.printStackTrace();
                throw new RuntimeException("Session update failed");
            }

            request.getSession().setAttribute("SUCCESS_MESSAGE", "Profile updated successfully!");
            response.sendRedirect(request.getContextPath() + "/profile");
            return;

        } catch (Exception e) {
            System.err.println("Error updating profile for user ID: " + currentUser.getId() + " - " + e.getMessage());
            e.printStackTrace();

            UserError userError = new UserError();
            userError.setError("An unexpected error occurred while updating your profile. Please try again.");
            request.setAttribute("USER_ERROR", userError);
            request.setAttribute("USER", currentUser);
            request.getRequestDispatcher("/profile/edit-profile.jsp").forward(request, response);
        }
    }


    private User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;
        Object obj = session.getAttribute("LOGIN_USER");
        return (obj instanceof User) ? (User) obj : null;
    }
}
