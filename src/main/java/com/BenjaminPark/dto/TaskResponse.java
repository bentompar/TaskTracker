package com.BenjaminPark.dto;

public class TaskResponse {
    final String taskId;
    final String taskName;
    final String taskDescription;
    final String taskStatus;
    final String dueDate;

    /**
     * Creates new task response object with due date set as null.
     * @param taskId Id of the task.
     * @param taskName Name of the task.
     * @param taskDescription Description of the task.
     * @param taskStatus Status of the task.
     */
    public TaskResponse(String taskId, String taskName, String taskDescription, String taskStatus) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
        this.dueDate = null;
    }

    /**
     * Creates new task response object
     * @param taskId Id of the task.
     * @param taskName Name of the task.
     * @param taskDescription Description of the task.
     * @param taskStatus Status of the task.
     * @param dueDate Due date of the task.
     */
    public TaskResponse(String taskId, String taskName, String taskDescription, String taskStatus, String dueDate) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
        this.dueDate = dueDate;
    }

    /**
     * Returns taskId of this task.
     */
    public String getTaskId() {
        return taskId;
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
