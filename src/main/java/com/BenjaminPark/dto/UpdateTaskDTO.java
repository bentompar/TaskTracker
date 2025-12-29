package com.BenjaminPark.dto;

public class UpdateTaskDTO {
    final String taskName;
    final String taskDescription;
    final String taskStatus;
    final String dueDate;

    /**
     * Creates task update object.
     * @param taskName Name of this task.
     * @param taskDescription Description of this task.
     * @param taskStatus Status of this task.
     * @param dueDate Due date of this task.
     */
    public UpdateTaskDTO(String taskName, String taskDescription, String taskStatus, String dueDate) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
        this.dueDate = dueDate;
    }

    /**
     * Returns taskName of this task.
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * Returns taskDescription of this task.
     */
    public String getTaskDescription() {
        return taskDescription;
    }

    /**
     * Returns taskStatus of this task.
     */
    public String getTaskStatus() {
        return taskStatus;
    }

    /**
     * Returns dueDate of this task.
     */
    public String getDueDate() {
        return dueDate;
    }
}