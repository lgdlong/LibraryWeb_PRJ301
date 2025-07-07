package dao;

import db.*;
import entity.*;
import enums.*;

import java.sql.*;
import java.time.*;
import java.util.*;

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

    public List<BookRequest> findAll() {
        String sql = "SELECT id, user_id, book_id, request_date, status FROM book_requests ORDER BY id ASC"; // FIFO: cái nào tạo trước thì lên đầu
        List<BookRequest> requests = new ArrayList<>();
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                BookRequest br = new BookRequest(
                    rs.getLong("id"),
                    rs.getLong("user_id"),
                    rs.getLong("book_id"),
                    rs.getDate("request_date").toLocalDate(),
                    RequestStatus.fromString(rs.getString("status"))
                );
                requests.add(br);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving book requests", e);
        }
        return requests;
    }

    public BookRequest findById(long id) {
        String sql = "SELECT * FROM book_requests WHERE id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new BookRequest(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getLong("book_id"),
                        rs.getDate("request_date").toLocalDate(),
                        RequestStatus.fromString(rs.getString("status"))
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving book request by ID", e);
        }
        return null;
    }

    public BookRequest findById(Connection transactionConnection, long id) {
        String sql = "SELECT * FROM book_requests WHERE id = ?";
        try (PreparedStatement stmt = transactionConnection.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new BookRequest(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getLong("book_id"),
                        rs.getDate("request_date").toLocalDate(),
                        RequestStatus.fromString(rs.getString("status"))
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving book request by ID", e);
        }
        return null;
    }

    public void updateStatus(long id, String newStatus) {
        String sql = "UPDATE book_requests SET status = ? WHERE id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus.toLowerCase());
            stmt.setLong(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating book request status", e);
        }
    }

    public void updateStatus(Connection transactionConn, long id, String newStatus) {
        String sql = "UPDATE book_requests SET status = ? WHERE id = ?";
        try (PreparedStatement stmt = transactionConn.prepareStatement(sql)) {

            stmt.setString(1, newStatus.toLowerCase());
            stmt.setLong(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating book request status", e);
        }
    }

    public List<BookRequest> viewBooksRequest(long userId) {
        List<BookRequest> list = new ArrayList<>();
        String sql = "SELECT id, user_id, book_id, request_date, status " +
            "FROM book_requests WHERE user_id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    long id = rs.getLong("id");
                    long uid = rs.getLong("user_id");
                    long bookId = rs.getLong("book_id");
                    LocalDate requestDate = rs.getDate("request_date").toLocalDate();
                    RequestStatus status = RequestStatus.fromString(rs.getString("status"));

                    BookRequest request = new BookRequest(id, uid, bookId, requestDate, status);
                    list.add(request);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving book requests by user ID", e);
        }

        return list;
    }
}
