package dto;

import enums.*;

public class UserDTO {
    private long id;
    private String name;
    private String email;
    private String role;    // lowercase string
    private String status;  // lowercase string

    public UserDTO() {
    }

    public UserDTO(long id, String name, String email, UserRole role, UserStatus status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role != null ? role.toString() : null;
        this.status = status != null ? status.toString() : null;
    }

    // Getter & Setter

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role != null ? role.toString() : null;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status != null ? status.toString() : null;
    }
}
