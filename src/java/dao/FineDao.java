package dao;

import db.*;
import entity.*;
import enums.*;

import java.sql.*;
import java.util.*;
import java.util.logging.*;

public class FineDao {
    private static final Logger LOGGER = Logger.getLogger(FineDao.class.getName());

    public List<Fine> getAll() {
        List<Fine> fines = new ArrayList<>();
        String sql = "SELECT id, borrow_id, fine_amount, paid_status FROM fines WHERE fine_amount > 0";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                fines.add(mapRow(rs));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching all fines", e);
            throw new RuntimeException(e);
        }

        return fines;
    }

    public Fine getById(long id) {
        String sql = "SELECT id, borrow_id, fine_amount, paid_status FROM fines WHERE id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching fine by ID", e);
            throw new RuntimeException(e);
        }

        return null;
    }

    public void add(Fine fine) {
        String sql = "INSERT INTO fines (borrow_id, fine_amount, paid_status) VALUES (?, ?, ?)";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, fine.getBorrowId());
            stmt.setDouble(2, fine.getFineAmount());
            stmt.setString(3, fine.getPaidStatus().toString());

            stmt.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding fine", e);
            throw new RuntimeException(e);
        }
    }

    public void update(Fine fine) {
        String sql = "UPDATE fines SET borrow_id = ?, fine_amount = ?, paid_status = ? WHERE id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, fine.getBorrowId());
            stmt.setDouble(2, fine.getFineAmount());
            stmt.setString(3, fine.getPaidStatus().toString());
            stmt.setLong(4, fine.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating fine", e);
            throw new RuntimeException(e);
        }
    }

    public void delete(long id) {
        String sql = "DELETE FROM fines WHERE id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting fine", e);
            throw new RuntimeException(e);
        }
    }

    public long countUnpaidFines() {
        String sql = "SELECT COUNT(*) FROM fines WHERE paid_status = 'UNPAID'";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error counting unpaid fines", e);
            throw new RuntimeException(e);
        }

        return 0;
    }

    private Fine mapRow(ResultSet rs) throws SQLException {
        return new Fine(
            rs.getLong("id"),
            rs.getLong("borrow_id"),
            rs.getDouble("fine_amount"),
            PaidStatus.fromString(rs.getString("paid_status"))
        );
    }
}
