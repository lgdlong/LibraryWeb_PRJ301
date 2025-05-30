package dao;

import db.*;

import java.sql.*;

public class BookDao {
    public long countAll() {
        String sql = "SELECT COUNT(*) FROM books";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting books", e);
        }
        return 0;
    }
}
