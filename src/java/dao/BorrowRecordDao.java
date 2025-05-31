package dao;

import db.*;
import entity.*;
import enums.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.logging.*;

public class BorrowRecordDao {
    private static final Logger LOGGER = Logger.getLogger(BorrowRecordDao.class.getName());

    public List<BorrowRecord> getAll() {
        List<BorrowRecord> records = new ArrayList<>();
        String sql = "SELECT id, user_id, book_id, borrow_date, due_date, return_date, status FROM borrow_records";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                records.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching all borrow records: " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Failed to fetch all borrow records", e);
            throw new RuntimeException(e);
        }

        return records;
    }

    public BorrowRecord getById(long id) {
        String sql = "SELECT id, user_id, book_id, borrow_date, due_date, return_date, status FROM borrow_records WHERE id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching borrow record by ID: " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Failed to fetch borrow record by ID", e);
            throw new RuntimeException(e);
        }

        return null;
    }

    public void add(BorrowRecord record) {
        if (record == null) {
            throw new IllegalArgumentException("BorrowRecord must not be null.");
        }

        String sql = "INSERT INTO borrow_records (user_id, book_id, borrow_date, due_date, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, record.getUserId());
            stmt.setLong(2, record.getBookId());
            stmt.setDate(3, Date.valueOf(record.getBorrowDate()));
            stmt.setDate(4, Date.valueOf(record.getDueDate()));
            stmt.setString(5, record.getStatus().toString());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error adding borrow record: " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Failed to add borrow record", e);
            throw new RuntimeException(e);
        }
    }

    public void update(BorrowRecord record) {
        if (record == null || record.getId() <= 0) {
            throw new IllegalArgumentException("BorrowRecord must not be null and must have a valid ID for update.");
        }

        String sql = "UPDATE borrow_records SET user_id = ?, book_id = ?, borrow_date = ?, due_date = ?, return_date = ?, status = ? WHERE id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, record.getUserId());
            stmt.setLong(2, record.getBookId());
            stmt.setDate(3, Date.valueOf(record.getBorrowDate()));
            stmt.setDate(4, Date.valueOf(record.getDueDate()));
            stmt.setDate(5, record.getReturnDate() != null ? Date.valueOf(record.getReturnDate()) : null);
            stmt.setString(6, record.getStatus().toString());
            stmt.setLong(7, record.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Update failed, no rows affected. Record with ID " + record.getId() + " may not exist.");
            }

        } catch (SQLException e) {
            System.err.println("Error updating borrow record: " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Failed to update borrow record", e);
            throw new RuntimeException(e);
        }
    }

    public void delete(long id) {
        String sql = "DELETE FROM borrow_records WHERE id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error deleting borrow record: " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Failed to delete borrow record", e);
            throw new RuntimeException(e);
        }
    }

    public List<Map.Entry<Long, Long>> getMostBorrowedBooks() {
        String sql = "SELECT book_id, COUNT(*) as borrow_count " +
            "FROM borrow_records " +
            "GROUP BY book_id " +
            "ORDER BY borrow_count DESC " +
            "LIMIT 5";

        List<Map.Entry<Long, Long>> mostBorrowed = new ArrayList<>();
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                long bookId = rs.getLong("book_id");
                long borrowCount = rs.getLong("borrow_count");
                mostBorrowed.add(new AbstractMap.SimpleEntry<>(bookId, borrowCount));
            }

            return mostBorrowed;

        } catch (SQLException e) {
            System.err.println("Error retrieving most borrowed books: " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Failed to retrieve most borrowed books", e);
            throw new RuntimeException(e);
        }
    }

    private BorrowRecord mapRow(ResultSet rs) throws SQLException {
        BorrowRecord record = new BorrowRecord();
        record.setId(rs.getLong("id"));
        record.setUserId(rs.getLong("user_id"));
        record.setBookId(rs.getLong("book_id"));

        Date borrowDate = rs.getDate("borrow_date");
        if (borrowDate == null) {
            throw new SQLException("borrow_date cannot be null for borrow record ID: " + rs.getLong("id"));
        }
        record.setBorrowDate(borrowDate.toLocalDate());

        Date dueDate = rs.getDate("due_date");
        if (dueDate == null) {
            throw new SQLException("due_date cannot be null for borrow record ID: " + rs.getLong("id"));
        }
        record.setDueDate(dueDate.toLocalDate());

        Date returnDate = rs.getDate("return_date");
        if (returnDate != null) {
            record.setReturnDate(returnDate.toLocalDate());
        }
        record.setStatus(parseBorrowStatus(rs.getString("status")));
        return record;
    }

    private BorrowStatus parseBorrowStatus(String statusStr) {
        try {
            return BorrowStatus.fromString(statusStr);
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Invalid borrow status in database: " + statusStr);
            return BorrowStatus.BORROWED; // Giá trị mặc định
        }
    }
}
