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
            // Always get updated values from form/request
            String fullname = request.getParameter("fullname");
            String email = request.getParameter("email");

            ProfileUpdateDTO profileDto = new ProfileUpdateDTO(currentUser.getId(), fullname, email);

            UserError userError = profileValidator.validateProfileUpdate(profileDto, currentUser);

            boolean hasError = (userError.getError() != null && !userError.getError().isEmpty())
                || (userError.getFullnameError() != null && !userError.getFullnameError().isEmpty())
                || (userError.getEmailError() != null && !userError.getEmailError().isEmpty());

            if (hasError) {
                request.setAttribute("USER_ERROR", userError);
                // Optional: re-fill form fields on error
                request.setAttribute("USER", profileDto); // Use DTO if JSP expects "USER"
                request.getRequestDispatcher("/profile/edit-profile.jsp").forward(request, response);
                return;
            }

            // Update the user's profile in DB and session
            try {
                userService.updateUserProfile(profileDto, currentUser, request.getSession());
            } catch (Exception ex) {
                System.err.println("Exception updating session user: " + ex.getMessage());
                ex.printStackTrace();
                throw new RuntimeException("Session update failed");
            }

            request.setAttribute("SUCCESS_MESSAGE", "Profile updated successfully!");
            // Optionally: set updated user for display
            request.setAttribute("USER", userService.getUserById(currentUser.getId()));
            // You may want to show a success message after redirect, see tip below!
            response.sendRedirect(request.getContextPath() + "/profile");

        } catch (Exception e) {
            System.err.println("Error updating profile for user ID: " + currentUser.getId() + " - " + e.getMessage());
            e.printStackTrace();

            UserError userError = new UserError();
            userError.setError("An unexpected error occurred while updating your profile. Please try again.");
            request.setAttribute("USER_ERROR", userError);
            request.setAttribute("USER", currentUser);
            // You may want to show a success message after redirect, see tip below!
            response.sendRedirect(request.getContextPath() + "/profile/update");
        }
    }

    private User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;
        Object obj = session.getAttribute("LOGIN_USER");
        return (obj instanceof User) ? (User) obj : null;
    }
}
