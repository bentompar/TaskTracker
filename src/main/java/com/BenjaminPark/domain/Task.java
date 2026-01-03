package com.BenjaminPark.domain;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents a task in the task tracker system.
 * Each task has a taskname, a hidden UUID and a status.
 * Also, possibly a description and duedate.
 */

@Entity
@Table(
        name = "tasks",
        indexes = {@Index(name = "idx_task_status", columnList = "taskStatus")
    })

public class Task {
    @Id
    private UUID taskId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false)
    private String taskName;

    @Column
    private String taskDescription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus taskStatus;

    @Column
    private LocalDate dueDate;

    protected Task() {}

    public Task(User owner, String taskName, String taskDescription, LocalDate dueDate) {
        this.owner = owner;
        this.taskId = UUID.randomUUID();
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = TaskStatus.OPEN;
        this.dueDate = dueDate;
    }

    public Task(User owner, String taskName, String taskDescription) {
        this (owner, taskName, taskDescription, null);
    }

    /**
     * Returns owner of this task.
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Sets owner of this task.
     * @param owner owner of this task.
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * Returns taskId of this task.
     */
    public UUID getTaskId() {
        return taskId;
    }

    /**
     * Returns taskname of this task.
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * Sets taskname of this task.
     * @param taskName taskname of this task.
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * Returns taskdescription of this task.
     */
    public String getTaskDescription() {
        return taskDescription;
    }

    /**
     * Sets taskdescription of this task.
     * @param taskDescription taskdescription of this task.
     */
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    /**
     * Returns taskstatus of this task.
     */
    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    /**
     * Sets taskstatus of this task.
     * @param taskStatus taskstatus of this task.
     */
    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    /**
     * Returns the duedate of this task.
     */
    public Optional<LocalDate> getDueDate() {
        return Optional.ofNullable(dueDate);
    }

    /**
     * Sets the duedate of this task.
     * @param dueDate duedate of this task.
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Determines whether task is equal to this object.
     * @param o   the reference object with which to compare.
     * @return true if object has same taskid.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;        // same reference
        if (!(o instanceof Task)) return false;  // type check
        Task task = (Task) o;
        return taskId != null && taskId.equals(task.taskId); // primary key check
    }

    /**
     * Returns the hashcode value of this task.
     * @return Hashcode value of this task.
     */
    @Override
    public int hashCode() {
        return taskId != null ? taskId.hashCode() : 0;
    }

    public String toString() {
        return taskId + " " + taskName;
    }
}
