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

    // Lấy tất cả borrow records (không join, chỉ bảng borrow_records)
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
            LOGGER.log(Level.SEVERE, "Failed to fetch all borrow records", e);
            throw new RuntimeException(e);
        }

        return records;
    }

    // Lấy tất cả bản ghi trạng thái "BORROWED"
    public List<BorrowRecord> getAllBorrowed() {
        List<BorrowRecord> records = new ArrayList<>();
        String sql = "SELECT id, user_id, book_id, borrow_date, due_date, return_date, status FROM borrow_records WHERE status = 'BORROWED'";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                records.add(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to fetch borrowed records", e);
            throw new RuntimeException(e);
        }

        return records;
    }

    // Lấy tất cả bản ghi trạng thái "OVERDUE"
    public List<BorrowRecord> getAllOverdue() {
        List<BorrowRecord> records = new ArrayList<>();
        String sql = "SELECT id, user_id, book_id, borrow_date, due_date, return_date, status FROM borrow_records WHERE status = 'overdue'";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                records.add(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to fetch overdue records", e);
            throw new RuntimeException(e);
        }

        return records;
    }

    // Lấy 1 bản ghi theo id
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
            LOGGER.log(Level.SEVERE, "Failed to add borrow record", e);
            throw new RuntimeException(e);
        }
    }

    public void add(Connection conn, BorrowRecord record) {
        if (record == null) {
            throw new IllegalArgumentException("BorrowRecord must not be null.");
        }
        if (conn == null) {
            throw new IllegalArgumentException("Connection must not be null.");
        }
        String sql = "INSERT INTO borrow_records (user_id, book_id, borrow_date, due_date, status) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, record.getUserId());
            stmt.setLong(2, record.getBookId());
            stmt.setDate(3, Date.valueOf(record.getBorrowDate()));
            stmt.setDate(4, Date.valueOf(record.getDueDate()));
            stmt.setString(5, record.getStatus().toString());

            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to add borrow record with provided connection", e);
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
            LOGGER.log(Level.SEVERE, "Failed to update borrow record", e);
            throw new RuntimeException(e);
        }
    }

    public void updateStatus(long id, BorrowStatus newStatus) {
        String sql = "UPDATE borrow_records SET status = ? WHERE id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus.toString());
            stmt.setLong(2, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Update failed, no rows affected. Record with ID " + id + " may not exist.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to update borrow record status", e);
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
            LOGGER.log(Level.SEVERE, "Failed to delete borrow record", e);
            throw new RuntimeException(e);
        }
    }

    public long countCurrentlyBorrowed() {
        String sql = "SELECT COUNT(*) FROM borrow_records WHERE status IN ('BORROWED', 'OVERDUE')";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error counting currently borrowed books", e);
            throw new RuntimeException(e);
        }

        return 0;
    }

    public List<Map.Entry<Long, Long>> getMostBorrowedBooks() {
        String sql = "SELECT TOP 5 book_id, COUNT(*) AS borrow_count " +
            "FROM borrow_records " +
            "GROUP BY book_id " +
            "ORDER BY borrow_count DESC";

        List<Map.Entry<Long, Long>> mostBorrowed = new ArrayList<>();
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                long bookId = rs.getLong("book_id");
                long borrowCount = rs.getLong("borrow_count");
                mostBorrowed.add(new AbstractMap.SimpleEntry<>(bookId, borrowCount));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to retrieve most borrowed books", e);
            throw new RuntimeException(e);
        }
        return mostBorrowed;
    }

    // Mapping ResultSet sang entity
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
            return BorrowStatus.BORROWED; // Giá trị mặc định an toàn
        }
    }

    public List<BorrowRecord> getBorrowHistoryByUserId(long userId) {
        List<BorrowRecord> history = new ArrayList<>();

        String sql =
            "SELECT br.id, br.user_id, br.book_id, br.borrow_date, br.due_date, br.return_date, br.status " +
                "FROM borrow_records br WHERE br.user_id = ? " +
                "ORDER BY br.borrow_date DESC";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    BorrowRecord record = mapRow(rs);
                    history.add(record);
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to fetch borrow history by user ID", e);
            throw new RuntimeException(e);
        }

        return history;
    }

    public int sendBorrowRequest(long userId, List<Book> books) {
        int total = 0;
        String sql = "insert [dbo].[book_requests] values(?,?,?,?)";

        try (Connection conn = DbConfig.getConnection()) {
            if (conn != null) {
                conn.setAutoCommit(false);

                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    Date requestDate = new Date(System.currentTimeMillis());

                    for (Book book : books) {
                        ps.setLong(1, userId);
                        ps.setLong(2, book.getId());
                        ps.setDate(3, requestDate);
                        ps.setString(4, "pending");
                        ps.addBatch();
                    }

                    int[] results = ps.executeBatch();
                    conn.commit();

                    for (int count : results) {
                        if (count >= 1) total++;
                    }
                } catch (SQLException e) {
                    conn.rollback();
                    e.printStackTrace();
                } finally {
                    conn.setAutoCommit(true);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return total;
    }
}




