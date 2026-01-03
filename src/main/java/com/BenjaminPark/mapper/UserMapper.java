package com.BenjaminPark.mapper;

import com.BenjaminPark.domain.User;
import com.BenjaminPark.dto.CreateUserDTO;
import com.BenjaminPark.dto.UpdateUserDTO;
import com.BenjaminPark.dto.UserResponse;

public class UserMapper {

    /**
     * Creates User entity from CreateUserDTO.
     * @param createUserDTO CreateUserDTO to convert into user entity.
     */
    public User fromCreateUserDTO(CreateUserDTO createUserDTO) {
        return new User(createUserDTO.getUsername(), createUserDTO.getPassword());
    }

    /**
     * Returns updated User entity from UpdateUserDTO.
     * @param user User to be updated.
     * @param updateUserDTO UpdateUserDTO to convert existing user entity.
     */
    public User applyUpdate(User user, UpdateUserDTO updateUserDTO) {
        user.setUsername(updateUserDTO.getUsername());
        return user;
    }

    /**
     * Returns UserResponse from user entity.
     * @param user User entity converted into UserResponse entity.
     */
    public UserResponse toUserResponse(User user) {
        return new UserResponse(user.getUserId().toString(), user.getUsername());
    }

}
