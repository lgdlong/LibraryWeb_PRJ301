# Profile Update Feature Implementation

## Overview

This implementation adds profile update functionality to the Library Web application, allowing users to edit their
profile information including name, email, and password.

## Components Created/Modified

### Frontend (JSP)

- **File**: `web/profile/view-profile.jsp`
- **Changes**:
    - Added "Edit Profile" button
    - Added profile edit form with validation
    - Added JavaScript for form show/hide functionality
    - Added client-side password confirmation validation
    - Added error message display

### Backend Components

#### 1. Controllers

- **ProfileUpdateController** (`src/java/controller/ProfileUpdateController.java`)
    - Handles POST requests to `/profile/update`
    - Validates user input
    - Updates user profile in database and session
    - Provides comprehensive error handling

#### 2. DTOs

- **ProfileUpdateDTO** (`src/java/dto/ProfileUpdateDTO.java`)
    - Data transfer object for profile update requests
    - Contains fullname, email, password, and confirmPassword fields
    - Includes helper method to check if password is being updated

#### 3. Mappers

- **ProfileMapping** (`src/java/mapper/ProfileMapping.java`)
    - Converts between ProfileUpdateDTO and User entity
    - Preserves user ID, role, and status during updates
    - Handles password update logic (new password vs. keep existing)

#### 4. Services

- **UserService** (`src/java/service/UserService.java`)
    - Added `updateProfile()` method for profile-specific updates
    - Includes validation and logging for profile updates

## Validation Rules

### Frontend (HTML5 + JavaScript)

- Full name: required, 5-30 characters
- Email: required, valid email format
- Password: optional, 5-30 characters if provided
- Confirm Password: must match password if password is provided

### Backend Validation

- Full name: required, 5-30 characters
- Email: required, must be unique (except current user's email)
- Password: 5-30 characters if provided
- Password confirmation: must match password

## URL Mappings

- **Profile View**: `/Profile` (GET) - shows profile page
- **Profile Update**: `/profile/update` (POST) - processes profile updates
- **Profile Update**: `/profile/update` (GET) - redirects to profile view

## Error Handling

- Validation errors are displayed in the edit form
- Database errors show generic error messages
- Authentication checks redirect to home page
- Form automatically shows edit mode when there are errors

## Security Features

- Authentication required for all profile operations
- Password is optional (can leave blank to keep current)
- Email uniqueness validation (except for current user)
- Session is updated with new user data after successful update

## User Experience

- Single-page profile view with expandable edit form
- Smooth scrolling to edit form when activated
- Client-side password confirmation validation
- Success/error messages displayed contextually
- Form preserves user input on validation errors

## Usage

1. User navigates to `/Profile`
2. Clicks "Edit Profile" button
3. Fills out the form (password optional)
4. Clicks "Save Changes"
5. System validates and updates profile
6. Success message shown or errors displayed for correction

## Files Modified

- `web/profile/view-profile.jsp` - Added edit form and JavaScript
- `src/java/controller/ProfileUpdateController.java` - Complete implementation
- `src/java/service/UserService.java` - Added updateProfile method

## Files Created

- `src/java/dto/ProfileUpdateDTO.java` - Profile update DTO
- `src/java/mapper/ProfileMapping.java` - Profile mapping utilities

## Dependencies

- Existing UserService, AuthService, UserDao
- Existing User entity and UserError DTO
- Jakarta Servlet API
- Bootstrap 5 (for styling)
