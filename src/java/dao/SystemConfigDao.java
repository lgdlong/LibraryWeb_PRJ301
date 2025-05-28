package dao;

import db.*;
import dto.*;
import entity.*;

import java.sql.*;
import java.util.*;

public class SystemConfigDao {
    public List<SystemConfigDb> getAll() {
        List<SystemConfigDb> configs = new ArrayList<>();
        String sql = "SELECT id, config_key, config_value, description FROM system_config";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                configs.add(new SystemConfigDb(
                    rs.getLong("id"),
                    rs.getString("config_key"),
                    rs.getString("config_value"),
                    rs.getString("description")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching system configurations", e);
        }

        return configs;
    }

    public void add(SystemConfig config) {
        String sql = "INSERT INTO system_config (config_key, config_value, description) VALUES (?, ?, ?)";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, config.getConfigKey());
            // Chuyển đổi kiễu dữ liệu từ double sang String
            stmt.setString(2, String.valueOf(config.getConfigValue()));
            stmt.setString(3, config.getDescription());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding system configuration", e);
        }
    }

    public void update(SystemConfig config) {
        String sql = "UPDATE system_config SET config_key = ?, config_value = ?, description = ? WHERE id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, config.getConfigKey());
            stmt.setString(2, String.valueOf(config.getConfigValue()));
            stmt.setString(3, config.getDescription());
            stmt.setLong(4, config.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating system configuration", e);
        }
    }

    public void delete(long id) {
        String sql = "DELETE FROM system_config WHERE id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting system configuration", e);
        }
    }
}
