package dao;

import db.*;
import entity.*;
import enums.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

public class UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDao.class.getName());

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, password, email, role, status FROM users";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching all users: " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Failed to fetch all users", e);
            throw new RuntimeException(e);
        }

        return users;
    }

    public User getById(long id) {
        String sql = "SELECT id, name, password, email, role, status FROM users WHERE id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching user by ID: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return null;
    }

    public User getByEmail(String email) {
        String sql = "SELECT id, name, password, email, role, status FROM users WHERE email = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching user by email: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<User> searchByKeyword(String keyword) {
        if (keyword == null) {
            return new ArrayList<>();
        }

        String sql = "SELECT id, name, password, email, role, status " +
            "FROM users " +
            "WHERE LOWER(name) LIKE ? OR LOWER(email) LIKE ?";
        String searchTerm = "%" + keyword.toLowerCase() + "%";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, searchTerm);
            stmt.setString(2, searchTerm);

            try (ResultSet rs = stmt.executeQuery()) {
                List<User> results = new ArrayList<>();

                while (rs.next()) {
                    results.add(mapRow(rs));
                }

                return results;
            }
        } catch (SQLException e) {
            System.err.println("Error searching users by keyword: " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Failed to search users by keyword", e);
            throw new RuntimeException("Search failed", e);
        }
    }

    public void add(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null.");
        }

        String sql = "INSERT INTO users (name, email, password, role, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole().toString());
            stmt.setString(5, user.getUserStatus().toString());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void update(User user) {
        if (user == null || user.getId() <= 0) {
            throw new IllegalArgumentException("User must not be null and must have a valid ID for update.");
        }

        String sql = "UPDATE users SET name = ?, email = ?, password = ?, role = ?, status = ? WHERE id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole().toString());
            stmt.setString(5, user.getUserStatus().toString());
            stmt.setLong(6, user.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void delete(long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public long userCount() {
        String sql = "SELECT COUNT(*) FROM users";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            System.err.println("Error counting users: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return 0;
    }

    private User mapRow(ResultSet rs) throws SQLException {
        return new User(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("password"),
            parseUserRole(rs.getString("role")),
            parseUserStatus(rs.getString("status"))
        );
    }

    private UserRole parseUserRole(String roleStr) {
        try {
            return UserRole.fromString(roleStr);
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Invalid user role in database: " + roleStr);
            return UserRole.USER; // or throw a more specific exception
        }
    }

    private UserStatus parseUserStatus(String statusStr) {
        try {
            return UserStatus.fromString(statusStr);
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Invalid user status in database: " + statusStr);
            return UserStatus.BLOCKED; // or throw a more specific exception
        }
    }
//    public static final String CHECK_LOGIN = "SELECT [id],[name],[email],[password],[role],[status] FROM users WHERE email=? AND password=?";
//    public User checkLogin(String email,String password) throws SQLException{
//        Connection cn = null;
//        PreparedStatement ptm = null;
//        ResultSet rs = null;
//        User result = null;
//        try {
//            cn = DbConfig.getConnection();
//            if(cn!=null){
//                ptm =  cn.prepareStatement(CHECK_LOGIN);
//                ptm.setString(1,email);
//                ptm.setString(2, password);
//                rs = ptm.executeQuery();
//                if(rs.next()){
//                    int id = rs.getInt("id");
//                    String name = rs.getString("name");
//                    UserRole role = parseUserRole(rs.getString("role"));
//                    UserStatus status= parseUserStatus(rs.getString("status"));
//                    result = new User(id, name, email, role,status );
//                }
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            rs.close();
//            ptm.close();
//            cn.close();
//        }
//        return result;
//    }
//    private static final String REGISTER =  "INSERT  INTO users ([name],[email],[password],[role],[status]) VALUES (?, ?, ?, 'user','active')";
//     public boolean insertUser(String fullName,String email,String password) throws SQLException{
//
//         Connection cn = null;
//         PreparedStatement st = null;
//         boolean check = false;
//         try {
//              cn=DbConfig.getConnection();
//            if(cn!=null){
//               st=cn.prepareStatement(REGISTER);
//               st.setString(1, fullName);
//               st.setString(2, email);
//               st.setString(3,password);
//               check = st.executeUpdate() > 0 ? true : false;
//
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally{
//         if(st!=null){
//             st.close();
//         }
//         if(cn!=null){
//             cn.close();
//         }
//
//         }
//        return check;
//    }


}
