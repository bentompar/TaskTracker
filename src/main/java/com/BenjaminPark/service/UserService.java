package com.BenjaminPark.service;

import com.BenjaminPark.domain.User;
import com.BenjaminPark.dto.CreateUserDTO;
import com.BenjaminPark.dto.UpdateUserDTO;
import com.BenjaminPark.exceptions.*;
import com.BenjaminPark.repository.TaskRepository;
import com.BenjaminPark.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * Adds user to database.
     * @param createUserDTO The dto containing the data of the user to be created.
     * @return User added to database.
     * @throws RuntimeException throws DuplicateUserIdException or DuplicateUsernameException
     */

    public User createUser(CreateUserDTO createUserDTO) throws RuntimeException {
        String passwordHash = passwordEncoder.encode(createUserDTO.getPassword());
        User user = new User(createUserDTO.getUsername(), passwordHash);
        userRepository.save(user);
        return user;
    }

    /**
     * Updates existing user from database.
     * @param userId userId of user updated.
     * @param updateUserDTO the dto containing the updated user data.
     * @return updated user.
     * @throws MissingUserIdException
     */
    public User updateUser(UUID userId, UpdateUserDTO updateUserDTO) throws MissingUserIdException {
        User oldUser = userRepository.findById(userId).orElseThrow(() -> new MissingUserIdException("UserId Not Found."));
        if (updateUserDTO.getUsername() != null) {
            oldUser.setUsername(updateUserDTO.getUsername());
        }
        if (updateUserDTO.getPassword() != null) {
            oldUser.replacePasswordHash(passwordEncoder.encode(updateUserDTO.getPassword()));
        }

        userRepository.save(oldUser);
        return oldUser;
    }

    /**
     * Deletes user from database.
     * @param userId userId of user deleted.
     * @return deleted user.
     * @throws InvalidPasswordException
     */
    public User deleteUser(UUID userId) throws MissingUserIdException {
        User user = userRepository.findById(userId).orElseThrow(() -> new MissingUserIdException("UserId Not Found."));
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


