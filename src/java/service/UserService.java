package service;


import dao.*;
import entity.*;
import enums.*;

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

    public void deleteUser(long id) {
        userDao.delete(id);
    }


}
