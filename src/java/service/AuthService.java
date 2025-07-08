package service;


import db.*;
import entity.*;
import enums.*;
import security.*;

import java.sql.*;

public class AuthService {
    private static final String CHECK_LOGIN = "SELECT [id],[name],[email],[password],[role],[status] FROM users WHERE email=? AND password=?";
    private static final String CHECK_EMAIL_EXISTS = "SELECT [id] FROM users WHERE email=?";

    public User checkLogin(String email, String password) throws SQLException {
        Connection cn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        User result = null;

        // Hash the password before checking
        password = PasswordHasher.hash(password);

        try {
            cn = DbConfig.getConnection();
            if (cn != null) {
                ptm = cn.prepareStatement(CHECK_LOGIN);
                ptm.setString(1, email);
                ptm.setString(2, password);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    UserRole role = parseUserRole(rs.getString("role"));
                    UserStatus status = parseUserStatus(rs.getString("status"));
                    result = new User(id, name, email, role, status);
                }
            }
        } catch (Exception e) {
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
}
