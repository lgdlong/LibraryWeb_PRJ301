package dao;

import db.*;

import java.sql.*;

public class BookRequestDao {
    public long countByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM book_requests WHERE status = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.toLowerCase());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting book requests by status", e);
        }
        return 0;
    }
}
