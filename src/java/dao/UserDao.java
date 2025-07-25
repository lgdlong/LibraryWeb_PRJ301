package dao;

import db.*;
import dto.*;
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

    public User getByEmail(String email) {
        String sql = "SELECT id, name, password, email, role, status FROM users WHERE email = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching user by email: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<User> searchByKeyword(String keyword) {
        if (keyword == null) {
            return new ArrayList<>();
        }

        String sql = "SELECT id, name, password, email, role, status " +
            "FROM users " +
            "WHERE LOWER(name) LIKE ? OR LOWER(email) LIKE ?";
        String searchTerm = "%" + keyword.toLowerCase() + "%";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, searchTerm);
            stmt.setString(2, searchTerm);

            try (ResultSet rs = stmt.executeQuery()) {
                List<User> results = new ArrayList<>();

                while (rs.next()) {
                    results.add(mapRow(rs));
                }

                return results;
            }
        } catch (SQLException e) {
            System.err.println("Error searching users by keyword: " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Failed to search users by keyword", e);
            throw new RuntimeException("Search failed", e);
        }
    }

    public List<User> getByStatus(UserStatus status) {
        if (status == null) {
            return getAll();
        }

        String sql = "SELECT id, name, password, email, role, status FROM users WHERE status = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.toString());

            try (ResultSet rs = stmt.executeQuery()) {
                List<User> users = new ArrayList<>();

                while (rs.next()) {
                    users.add(mapRow(rs));
                }

                return users;
            }
        } catch (SQLException e) {
            System.err.println("Error fetching users by status: " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Failed to fetch users by status", e);
            throw new RuntimeException(e);
        }
    }

    public void add(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null.");
        }

        String sql = "INSERT INTO users (name, email, password, role, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole().toString());
            stmt.setString(5, user.getUserStatus().toString());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void update(User user) {
        if (user == null || user.getId() <= 0) {
            throw new IllegalArgumentException("User must not be null and must have a valid ID for update.");
        }

        String sql = "UPDATE users SET name = ?, email = ?, password = ?, role = ?, status = ? WHERE id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole().toString());
            stmt.setString(5, user.getUserStatus().toString());
            stmt.setLong(6, user.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void updateInfoForUser(ProfileUpdateDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("ProfileUpdateDTO cannot be null");
        }
        if (dto.getId() <= 0) {
            throw new IllegalArgumentException("User ID must be positive");
        }

        String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dto.getName());
            stmt.setString(2, dto.getEmail());
            stmt.setLong(3, dto.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("No user found with ID: " + dto.getId());
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to update user info for ID: " + dto.getId(), e);
            throw new RuntimeException(e);
        }
    }


    public void delete(long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            throw new RuntimeException(e);
        }
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
            return UserRole.fromString(roleStr);
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Invalid user role in database: " + roleStr);
            return UserRole.USER; // or throw a more specific exception
        }
    }

    private UserStatus parseUserStatus(String statusStr) {
        try {
            return UserStatus.fromString(statusStr);
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Invalid user status in database: " + statusStr);
            return UserStatus.BLOCKED; // or throw a more specific exception
        }
    }
    /**
     * Upgrades a user's password to a new hash by email.
     * @param email The user's email
     * @param newHashedPassword The new hashed password
     * @return true if update was successful, false otherwise
     */
    public boolean upgradePasswordToHash(String email, String newHashedPassword) {
        String sql = "UPDATE users SET password = ? WHERE email = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newHashedPassword);
            stmt.setString(2, email);
            int updated = stmt.executeUpdate();
            if (updated > 0) {
                LOGGER.info("Password upgraded to BCrypt hash for user: " + email);
                return true;
            } else {
                LOGGER.warning("Failed to upgrade password for user: " + email);
                return false;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error upgrading password for user: " + email, e);
            return false;
        }
    }



}
