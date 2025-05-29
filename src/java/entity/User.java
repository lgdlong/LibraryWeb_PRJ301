package entity;

import enums.*;

public class User {
    private long id;
    private String name;
    private String email;
    private String password;
    private UserRole role;
    private UserStatus userStatus;

    // Default constructor
    public User() {
    }

    // Constructor for creating a user
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = UserRole.USER; // Default role
        this.userStatus = UserStatus.ACTIVE; // Default status
    }

    // Constructor for retrieving a user from the database
    public User(long id, String name, String email, String password, UserRole role, UserStatus userStatus) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.userStatus = userStatus;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

}
