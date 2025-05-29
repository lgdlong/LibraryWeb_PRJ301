package service;


import dao.*;
import entity.*;

import java.util.*;

public class UserService {
    private final UserDao userDAO = new UserDao();

    public List<User> getAllUsers() {
        return userDAO.getAll();
    }

    public User getUserById(long id) {
        return userDAO.getById(id);
    }

    public long getUserCount() {
        return userDAO.userCount();
    }

    public User searchByEmail(String email) {
        return userDAO.getByEmail(email);
    }

    public void addUser(User user) {
        try {
            // check if user already exists
            if (userDAO.getByEmail(user.getEmail()) != null) {
                throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists.");
            }

            userDAO.add(user);
            System.out.println("User added: " + user.getEmail());

        } catch (RuntimeException e) {
            System.err.println("Error adding user: " + e.getMessage());
        }
    }

    public void updateUser(User user) {
        userDAO.update(user);
    }

    public void deleteUser(long id) {
        userDAO.delete(id);
    }

}
