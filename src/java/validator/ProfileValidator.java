package validator;

import dto.*;
import entity.*;
import service.*;

import java.sql.*;

public class ProfileValidator {

    private final AuthService authService;

    public ProfileValidator(AuthService authService) {
        this.authService = authService;
    }

    public UserError validateProfileUpdate(ProfileUpdateDTO profileDto, User currentUser) {
        UserError userError = new UserError();

        try {
            // Validate name
            if (profileDto.getName() == null || profileDto.getName().trim().isEmpty()) {
                userError.setNameError("Full name is required.");
            } else if (profileDto.getName().trim().length() < 5 || profileDto.getName().trim().length() > 30) {
                userError.setNameError("Full name must be between 5-30 characters.");
            }

            // Validate email
            if (profileDto.getEmail() == null || profileDto.getEmail().trim().isEmpty()) {
                userError.setEmailError("Email is required.");
            } else {
                // Check if the email is changed and if a new email already exists
                if (!profileDto.getEmail().equals(currentUser.getEmail())) {
                    if (authService.emailExists(profileDto.getEmail())) {
                        userError.setEmailError("Email already exists. Please choose a different email.");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error during validation: " + e.getMessage());
            e.printStackTrace();
            userError.setError("Database error occurred during validation. Please try again.");
        }

        return userError;
    }
}
