package com.BenjaminPark.domain;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCrypt;
import java.util.UUID;

/**
 * Represents a user in the task tracker system.
 * Each user has a unique username, a hidden UUID, and a hashed password.
 */

@Entity
@Table(name = "users")
public class User {


    @Id
    private UUID userId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String userPassword;

    protected User() {}

    public User(String username, String password) {
        this.userId = UUID.randomUUID();
        this.username = username;
        this.userPassword = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Returns username of this user.

     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username of this user.
     * @param username username of this user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns userId of this user.
     */
    public UUID getUserId() {
        return userId;
    }

    /**
     * Checks if password passed matches this user's password.
     * @param password password of this user.
     */
    public boolean checkUserPassword(String password) {
        return BCrypt.checkpw(password, userPassword);
    }

    /**
     * Returns password of this user.
     * Intended for internal use only.
     */
    private String getUserPassword() {
        return userPassword;
    }
}
