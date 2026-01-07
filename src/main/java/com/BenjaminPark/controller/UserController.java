package com.BenjaminPark.controller;

import com.BenjaminPark.domain.User;
import com.BenjaminPark.dto.CreateUserDTO;
import com.BenjaminPark.dto.UpdateUserDTO;
import com.BenjaminPark.dto.UserResponse;
import com.BenjaminPark.mapper.UserMapper;
import com.BenjaminPark.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller responsible for managing users.
 *
 * <p>Exposes endpoints to create, retrieve, update, and delete user resources.
 * This controller operates on DTOs for request and response payloads and
 * delegates business logic to the {@link UserService}.</p>
 */

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    /**
     * Creates a new user.
     *
     * <p>Accepts user creation data and persists a new user resource.
     * On success, returns a representation of the created user.</p>
     *
     * @param createUserDTO DTO containing data required to create a user
     * @return a {@link ResponseEntity} containing the created user and HTTP 201 (Created)
     */

    @PostMapping
    public ResponseEntity<UserResponse> addUser(@RequestBody CreateUserDTO createUserDTO) {
        User user = userMapper.fromCreateUserDTO(createUserDTO);
        UserResponse userResponse = userMapper.toUserResponse(userService.createUser(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    /**
     * Retrieves a user by its unique identifier.
     *
     * <p>Returns the user representation corresponding to the given user ID.</p>
     *
     * @param userId the UUID of the user to retrieve
     * @return a {@link ResponseEntity} containing the user and HTTP 200 (OK)
     */

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String userId) {
        UserResponse userResponse = userMapper.toUserResponse(userService.getUserById(UUID.fromString(userId)));
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    /**
     * Updates an existing user.
     *
     * <p>Applies the provided update data to the user identified by the given ID
     * and returns the updated user representation.</p>
     *
     * @param updateUserDTO DTO containing fields to update
     * @param userId the UUID of the user to update
     * @return a {@link ResponseEntity} containing the updated user and HTTP 200 (OK)
     */

    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UpdateUserDTO updateUserDTO, @PathVariable String userId) {
        User user = userMapper.applyUpdate(userService.getUserById(UUID.fromString(userId)), updateUserDTO);
        UserResponse userResponse = userMapper.toUserResponse(user);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    /**
     * Deletes a user.
     *
     * <p>Deletes the user identified by the given ID after verifying credentials
     * and returns a representation of the deleted user.</p>
     *
     * @param userId the UUID of the user to delete
     * @param password the user's password for verification
     * @return a {@link ResponseEntity} containing the deleted user and HTTP 200 (OK)
     */

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable String userId, @RequestBody String password) {
        User user = userService.deleteUser(UUID.fromString(userId), password);
        UserResponse userResponse = userMapper.toUserResponse(user);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

}
