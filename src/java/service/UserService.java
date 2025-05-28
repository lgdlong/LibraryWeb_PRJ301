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
}
