package service;


import dao.*;
import dto.*;
import entity.*;
import enums.*;
import jakarta.servlet.http.*;

import java.util.*;

public class UserService {
    private final UserDao userDao = new UserDao();

    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    public User getUserById(long id) {
        return userDao.getById(id);
    }

    public long getUserCount() {
        return userDao.userCount();
    }

    public User searchByEmail(String email) {
        return userDao.getByEmail(email);
    }

    public List<User> getUsersByStatus(String status) {
        if (status == null || status.trim().isEmpty() || "all".equalsIgnoreCase(status)) {
            return userDao.getAll();
        }

        try {
            UserStatus userStatus = UserStatus.fromString(status);
            return userDao.getByStatus(userStatus);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid status input: " + status + ". Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<User> searchByNameOrEmail(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return userDao.searchByKeyword(keyword);
    }

    public void addUser(User user) {
        try {
            // check if user already exists
            if (userDao.getByEmail(user.getEmail()) != null) {
                throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists.");
            }

            userDao.add(user);
            System.out.println("User added: " + user.getEmail());

        } catch (RuntimeException e) {
            System.err.println("Error adding user: " + e.getMessage());
        }
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public void updateProfile(ProfileUpdateDTO dto) {
        if (dto == null || dto.getId() <= 0) {
            throw new IllegalArgumentException("Invalid user data for profile update");
        }

        userDao.updateInfoForUser(dto);
        System.out.println("Profile updated for user ID: " + dto.getId());
    }

    public void deleteUser(long id) {
        userDao.delete(id);
    }

    public void updateUserProfile(ProfileUpdateDTO profileDto, User currentUser, HttpSession session) {
        updateProfile(profileDto);

        try {
            if (!updateSessionUser(session, currentUser.getId())) {
                System.err.println("Failed to update session user after profile update");
                throw new RuntimeException("Session update failed after profile update");
            }
        } catch (Exception ex) {
            System.err.println("Exception when updating session user: " + ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException("Session update failed", ex);
        }

        System.out.println("Profile update attempted for user ID: " + currentUser.getId());
    }


    public boolean updateSessionUser(HttpSession session, long userId) {
        try {
            User user = userDao.getById(userId);
            if (user != null) {
                session.setAttribute("LOGIN_USER", user);
                return true;
            }
            return false;
        } catch (Exception ex) {
            System.err.println("Error while updating session user: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

}
