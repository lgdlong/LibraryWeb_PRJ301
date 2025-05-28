package dao;

import db.*;
import dto.*;

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
}
