package dao;

import db.*;
import entity.*;
import enums.*;

import java.sql.*;
import java.util.*;
import java.util.logging.*;

public class BookDao {
    private static final Logger LOGGER = Logger.getLogger(BookDao.class.getName());

    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                books.add(mapRow(rs));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching all books", e);
            throw new RuntimeException(e);
        }

        return books;
    }

    public Book getById(long id) {
        String sql = "SELECT * FROM books WHERE id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching book by ID", e);
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<Book> searchByKeyword(String keyword) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE LOWER(title) LIKE ? OR LOWER(author) LIKE ?";
        String searchTerm = "%" + keyword.toLowerCase() + "%";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, searchTerm);
            stmt.setString(2, searchTerm);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    books.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error searching books", e);
            throw new RuntimeException(e);
        }

        return books;
    }

    public void add(Book book) {
        String sql = "INSERT INTO books (title, author, isbn, cover_url, category, published_year, total_copies, available_copies, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setStatementParams(stmt, book);
            stmt.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding book", e);
            throw new RuntimeException(e);
        }
    }

    public void update(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, isbn = ?, cover_url = ?, category = ?, published_year = ?, total_copies = ?, available_copies = ?, status = ? WHERE id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setStatementParams(stmt, book);
            stmt.setLong(10, book.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating book", e);
            throw new RuntimeException(e);
        }
    }

    public void delete(long id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting book", e);
            throw new RuntimeException(e);
        }
    }

    public long bookCount() {
        String sql = "SELECT COUNT(*) FROM books";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            System.err.println("Error counting books: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return 0;
    }

    public long countAll() {
        return bookCount(); // hoặc copy nội dung từ bookCount nếu muốn tách biệt logic
    }

    public List<Book> sortBy(String field, boolean ascending) {
        List<String> allowedFields = Arrays.asList("title", "author", "published_year", "category");
        if (!allowedFields.contains(field)) {
            throw new IllegalArgumentException("Invalid sort field: " + field);
        }

        String order = ascending ? "ASC" : "DESC";
        String sql = "SELECT * FROM books ORDER BY " + field + " " + order;

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<Book> sortedBooks = new ArrayList<>();
            while (rs.next()) {
                sortedBooks.add(mapRow(rs)); // Bạn cần có hàm mapRow để chuyển ResultSet -> Book
            }
            return sortedBooks;

        } catch (SQLException e) {
            System.err.println("Error sorting books: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    private Book mapRow(ResultSet rs) throws SQLException {
        return new Book(
            rs.getLong("id"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getString("isbn"),
            rs.getString("cover_url"),
            rs.getString("category"),
            rs.getInt("published_year"),
            rs.getInt("total_copies"),
            rs.getInt("available_copies"),
            BookStatus.fromString(rs.getString("status"))
        );
    }

    private void setStatementParams(PreparedStatement stmt, Book book) throws SQLException {
        stmt.setString(1, book.getTitle());
        stmt.setString(2, book.getAuthor());
        stmt.setString(3, book.getIsbn());
        stmt.setString(4, book.getCoverUrl());
        stmt.setString(5, book.getCategory());
        stmt.setInt(6, book.getPublishedYear());
        stmt.setInt(7, book.getTotalCopies());
        stmt.setInt(8, book.getAvailableCopies());
        stmt.setString(9, book.getStatus().toString());
    }
}
