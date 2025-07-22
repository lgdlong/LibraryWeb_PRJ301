package service;


import db.*;
import entity.*;
import enums.*;
import security.*;

import java.sql.*;

public class AuthService {
    private static final String GET_USER_BY_EMAIL = "SELECT [id],[name],[email],[password],[role],[status] FROM users WHERE email=?";
    private static final String CHECK_EMAIL_EXISTS = "SELECT [id] FROM users WHERE email=?";

    private final dao.UserDao userDao = new dao.UserDao();

    public User checkLogin(String email, String password) throws SQLException {
        Connection cn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        User result = null;

        try {
            cn = DbConfig.getConnection();
            if (cn != null) {
                // First, get the user by email
                ptm = cn.prepareStatement(GET_USER_BY_EMAIL);
                ptm.setString(1, email);
                rs = ptm.executeQuery();

                if (rs.next()) {
                    String storedHashedPassword = rs.getString("password");

                    // Try BCrypt verification first
                    boolean isValidPassword = false;
                    if (storedHashedPassword.startsWith("$2a$") || storedHashedPassword.startsWith("$2b$") ||
                        storedHashedPassword.startsWith("$2x$") || storedHashedPassword.startsWith("$2y$")) {
                        // Password is already hashed with BCrypt
                        isValidPassword = PasswordHasher.matches(password, storedHashedPassword);
                    } else {
                        // Legacy plaintext password - compare directly and upgrade to hash
                        if (password.equals(storedHashedPassword)) {
                            isValidPassword = true;
                            // Upgrade password to hash
                            String newHashedPassword = PasswordHasher.hash(password);
                            upgradePasswordToHash(email, newHashedPassword);
                        }
                    }

                    if (isValidPassword) {
                        // Password matches, create user object
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        UserRole role = parseUserRole(rs.getString("role"));
                        UserStatus status = parseUserStatus(rs.getString("status"));
                        result = new User(id, name, email, role, status);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error in checkLogin: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (cn != null) cn.close();
        }
        return result;
    }

    public boolean emailExists(String email) throws SQLException {
        Connection cn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            cn = DbConfig.getConnection();
            if (cn != null) {
                ptm = cn.prepareStatement(CHECK_EMAIL_EXISTS);
                ptm.setString(1, email);
                rs = ptm.executeQuery();
                exists = rs.next();
            }
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (cn != null) cn.close();
        }

        return exists;
    }

    // Helper method: chuyển string thành enum (viết riêng nếu enum không có sẵn parser)
    private UserRole parseUserRole(String role) {
        try {
            return UserRole.valueOf(role.toUpperCase());
        } catch (Exception e) {
            return UserRole.USER; // default
        }
    }

    private UserStatus parseUserStatus(String status) {
        try {
            return UserStatus.valueOf(status.toUpperCase());
        } catch (Exception e) {
            return UserStatus.ACTIVE; // default
        }
    }

    private void upgradePasswordToHash(String email, String newHashedPassword) {
        boolean success = userDao.upgradePasswordToHash(email, newHashedPassword);
        if (success) {
            System.out.println("Password upgraded to BCrypt hash for user: " + email);
        } else {
            System.err.println("Failed to upgrade password for user: " + email);
        }
    }
}
