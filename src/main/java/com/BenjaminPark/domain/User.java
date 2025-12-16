package com.BenjaminPark.domain;

import com.BenjaminPark.exceptions.DuplicateTaskException;
import com.BenjaminPark.exceptions.MissingTaskException;
import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Collections;
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
     * Returns an unmodifiable list of tasks of this user.
     */
    public List<Task> getTasks() {
        return Collections.unmodifiableList(tasks);
    }

    /**
     * Adds task to this user's task list.
     * @param task task to add to this user's task list.
     */
    public void addTask(Task task) throws DuplicateTaskException {
        task.setOwner(this);
        if (!tasks.contains(task)) {
            tasks.add(task);
        } else {
            throw new DuplicateTaskException("Task already exists in this user's task list");
        }
    }

    /**
     * Removes task from this user's task list.
     * @param task task to remove from this user's task list.
     * @throws Exception No matching task found in this user's task list.
     */
    public void removeTask(Task task) throws MissingTaskException {
        if (!tasks.remove(task)) {
            throw new MissingTaskException("Task to be removed does not exist in this user's task list");
        }
    }

    /**
     * Updates task from this user's task list.
     * @param task task to update, contains same taskid with updated details.
     * @throws Exception No mathcing task found in this user's task list.
     */
    public void updateTask(Task task) throws MissingTaskException {
        if (tasks.contains(task)) {
            tasks.remove(task);
            tasks.add(task);
        } else {
            throw new MissingTaskException("Task to be updated does not exist in this user's task list");
        }
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

    public String toString() {
        return userId + " " + username;
    }
}
