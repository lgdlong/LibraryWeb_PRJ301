package mapper;

import dto.*;
import entity.*;
import enums.*;

public class UserMapping {
    public static UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }

        return new UserDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole(),
            user.getUserStatus()
        );
    }

    public static User toUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        UserRole role = userDTO.getRole() != null ? UserRole.fromString(userDTO.getRole()) : null;
        UserStatus status = userDTO.getStatus() != null ? UserStatus.fromString(userDTO.getStatus()) : null;

        return new User(
            userDTO.getId(),
            userDTO.getName(),
            userDTO.getEmail(),
            role,
            status
        );
    }
}
