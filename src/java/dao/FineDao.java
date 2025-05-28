package dao;

import db.*;

import java.sql.*;

public class FineDao {
    public long countUnpaid() {
        String sql = "SELECT COUNT(*) FROM fines WHERE paid_status = 'unpaid'";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting unpaid fines", e);
        }
        return 0;
    }
}
