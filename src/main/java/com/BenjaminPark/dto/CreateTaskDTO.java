package com.BenjaminPark.dto;

public class CreateTaskDTO {
    final String taskName;
    final String taskDescription;
    final String dueDate;

    /**
     * Creates task creation object without due date.
     * @param taskName Name of this task.
     * @param taskDescription Description of this task.
     */
    public CreateTaskDTO(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.dueDate = null;
    }

    /**
     * Creates task creation object with due date.
     * @param taskName Name of this task.
     * @param taskDescription Description of this task.
     * @param taskStatus Status of this task.
     * @param dueDate Due date of this task.
     */
    public CreateTaskDTO(String taskName, String taskDescription, String taskStatus, String dueDate) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
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
     * Returns dueDate of this task.
     */
    public String getDueDate() {
        return dueDate;
    }
}
