package dao;

import db.*;
import entity.*;
import enums.*;

import java.sql.*;
import java.util.*;

public class UserDao {
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching all users: " + e.getMessage());
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

    private User mapRow(ResultSet rs) throws SQLException {
        return new User(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("password"),
            UserRole.valueOf(rs.getString("role")),
            UserStatus.valueOf(rs.getString("status"))
        );
    }
}
