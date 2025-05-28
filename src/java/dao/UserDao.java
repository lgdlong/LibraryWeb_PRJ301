package dao;

import db.*;
import entity.*;
import enums.*;

import java.sql.*;
import java.util.*;
import java.util.logging.*;

public class UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDao.class.getName());

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, password, email, role, status FROM users";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching all users: " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Failed to fetch all users", e);
            throw new RuntimeException(e);
        }

        return users;
    }

    public User getById(long id) {
        String sql = "SELECT id, name, password, email, role, status FROM users WHERE id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching user by ID: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return null;
    }

    public long userCount() {
        String sql = "SELECT COUNT(*) FROM users";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            System.err.println("Error counting users: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return 0;
    }

    private User mapRow(ResultSet rs) throws SQLException {
        return new User(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("password"),
            parseUserRole(rs.getString("role")),
            parseUserStatus(rs.getString("status"))
        );
    }

    private UserRole parseUserRole(String roleStr) {
        try {
            return UserRole.valueOf(roleStr);
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Invalid user role in database: " + roleStr);
            return UserRole.USER; // or throw a more specific exception
        }
    }

    private UserStatus parseUserStatus(String statusStr) {
        try {
            return UserStatus.valueOf(statusStr);
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Invalid user status in database: " + statusStr);
            return UserStatus.BLOCKED; // or throw a more specific exception
        }
    }
}
