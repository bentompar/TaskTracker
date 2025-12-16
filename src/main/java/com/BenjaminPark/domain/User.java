package com.BenjaminPark.domain;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    List<Task> tasks;

    protected User() {}

    public User(String username, String password) {
        this.userId = UUID.randomUUID();
        this.username = username;
        this.userPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        this.tasks = new ArrayList<>();
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

    /**
     * Returns list of tasks of this user.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Determines whether user is equal to this object.
     * @param o   the reference object with which to compare.
     * @return true if object has same userid.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;        // same reference
        if (!(o instanceof User)) return false;  // type check
        User user = (User) o;
        return userId != null && userId.equals(user.userId); // primary key check
    }

    /**
     * Returns the hashcode value of this user.
     * @return Hashcode value of this user.
     */
    @Override
    public int hashCode() {
        return userId != null ? userId.hashCode() : 0;
    }
}
