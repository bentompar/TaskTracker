package com.BenjaminPark.service;

import com.BenjaminPark.domain.User;
import com.BenjaminPark.exceptions.*;
import com.BenjaminPark.repository.TaskRepository;
import com.BenjaminPark.repository.UserRepository;

import java.util.UUID;

public class UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public UserService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }


    /**
     * Adds user to database.
     * @param user User added to database.
     * @return User added to database.
     * @throws RuntimeException throws DuplicateUserIdException or DuplicateUsernameException
     */

    public User createUser(User user) throws RuntimeException {
        if (userRepository.existsById(user.getUserId())) {
            throw new DuplicateUserIdException("UserId already exists.");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateUsernameException("Username already exists.");
        }
        userRepository.save(user);
        return user;
    }

    /**
     * Updates existing user from database.
     * @param userId userId of user updated.
     * @param updatedUser user containing updated user details.
     * @param oldPassword old user password.
     * @param newPassword new user password.
     * @return updated user.
     * @throws MissingUserIdException
     */
    public User updateUser(UUID userId, User updatedUser, String oldPassword, String newPassword) throws MissingUserIdException {
        User oldUser = userRepository.findById(userId).orElseThrow(() -> new MissingUserIdException("UserId Not Found."));
        oldUser.setUsername(updatedUser.getUsername());
        oldUser.changeUserPassword(oldPassword, newPassword);

        userRepository.save(oldUser);
        return oldUser;
    }

    /**
     * Deletes user from database.
     * @param userId userId of user deleted.
     * @param password password of user deleted.
     * @return deleted user.
     * @throws InvalidPasswordException
     */
    public User deleteUser(UUID userId, String password) throws RuntimeException {
        User user = userRepository.findById(userId).orElseThrow(() -> new MissingUserIdException("UserId Not Found."));
        if (!user.checkUserPassword(password)) {
            throw new InvalidPasswordException("Invalid Password");
        }
        userRepository.delete(user);
        return user;
    }

    /**
     * Returns user by userId
     * @param id userId of user returned.
     * @return user
     * @throws MissingUserIdException
     */
    public User getUserById(UUID id) throws MissingUserIdException {
        return userRepository.findById(id).orElseThrow(() ->
                new MissingUserIdException("User with id " + id + " does not exist"));
    }

    /**
     * Returns user by username
     * @param username username of user returned.
     * @return user
     * @throws MissingUsernameException
     */
    public User getUserByUsername(String username) throws MissingUsernameException {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new MissingUsernameException("User with username " + username + " not found.")
        );
    }
}


