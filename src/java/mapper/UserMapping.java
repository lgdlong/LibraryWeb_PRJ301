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

    UserRole role = null;
    UserStatus status = null;

    try {
        role = userDTO.getRole() != null ? UserRole.fromString(userDTO.getRole()) : null;
        status = userDTO.getStatus() != null ? UserStatus.fromString(userDTO.getStatus()) : null;
    } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Invalid enum value in UserDTO: " + e.getMessage(), e);
    }

    return new User(
        userDTO.getId(),
        userDTO.getName(),
        userDTO.getEmail(),
        role,
        status
    );
}
}
