package dto;

public class ProfileUpdateDTO {
    private long id;
    private String fullname;
    private String email;

    public ProfileUpdateDTO() {
    }

    public ProfileUpdateDTO(long id, String fullname, String email) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ProfileUpdateDTO{" +
            "id=" + id +
            ", fullname='" + fullname + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}
