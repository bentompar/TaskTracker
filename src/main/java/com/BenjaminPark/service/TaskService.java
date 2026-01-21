package com.BenjaminPark.service;

import com.BenjaminPark.domain.Task;
import com.BenjaminPark.domain.TaskStatus;
import com.BenjaminPark.domain.User;
import com.BenjaminPark.dto.CreateTaskDTO;
import com.BenjaminPark.dto.UpdateTaskDTO;
import com.BenjaminPark.exceptions.*;
import com.BenjaminPark.repository.TaskRepository;
import com.BenjaminPark.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public TaskService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @PreAuthorize("#userId == principal.userId")
    public Task createTask(UUID userId, CreateTaskDTO createTaskDTO) throws DuplicateTaskException, InvalidDueDateException {
        User owner = userRepository.findById(userId).orElseThrow(() ->
                new MissingUserIdException("UserId Not Found."));
        LocalDate date = null;
        if (createTaskDTO.getDueDate() != null && !createTaskDTO.getDueDate().isEmpty()) {
            try {
                date = LocalDate.parse(createTaskDTO.getDueDate().trim());
            } catch (DateTimeParseException e) {
                throw new InvalidDueDateException("Invalid date format: " + createTaskDTO.getDueDate());
            }
        }
        Task task = new Task(owner, createTaskDTO.getTaskName(),
                createTaskDTO.getTaskDescription(), date);
        return taskRepository.save(task);
    }

    @PreAuthorize("#userId == principal.userId")
    public Task updateTask(UUID userId, UUID taskId, UpdateTaskDTO updateTaskDTO) throws MissingTaskException, InvalidUserException {
        Task taskToUpdate = taskRepository.findById(taskId).orElseThrow(() ->
                new MissingTaskException("Task with id " + taskId + " does not exist"));
        if (!taskToUpdate.getOwner().getUserId().equals(userId)) {
            throw new InvalidUserException("User does not own this task.");
        }
        taskToUpdate.setTaskName(updateTaskDTO.getTaskName().trim());
        taskToUpdate.setTaskDescription(updateTaskDTO.getTaskDescription().trim());
        taskToUpdate.setTaskStatus(TaskStatus.valueOf(updateTaskDTO.getTaskStatus()));
        LocalDate date = null;
        if (updateTaskDTO.getDueDate() != null && !updateTaskDTO.getDueDate().isEmpty()) {
            try {
                date = LocalDate.parse(updateTaskDTO.getDueDate().trim());
            }  catch (DateTimeParseException e) {
                throw new InvalidDueDateException("Invalid date format: " + updateTaskDTO.getDueDate());
            }
        }
        if (date != null) {
            taskToUpdate.setDueDate(date);
        }
        return taskRepository.save(taskToUpdate);
    }

    @PreAuthorize("#userId == principal.userId")
    public Task deleteTask(UUID userId, UUID taskId) throws MissingTaskException, InvalidUserException {
        Task taskToDelete = taskRepository.findById(taskId).orElseThrow(() ->
                new MissingTaskException("Task with id " + taskId + " does not exist"));
        if (!taskToDelete.getOwner().getUserId().equals(userId)) {
            throw new InvalidUserException("User does not own this task.");
        }
        taskRepository.delete(taskToDelete);
        return taskToDelete;
    }

    @PreAuthorize("#userId == principal.userId")
    public Task getTaskByTaskId(UUID userId, UUID taskId) throws MissingTaskException {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new MissingTaskException("Task with id " + taskId + " does not exist."));
        if (!task.getOwner().getUserId().equals(userId)) {
            throw new InvalidUserException("User does not own this task.");
        }
        return task;
    }

    @PreAuthorize("#userId == principal.userId")
    public List<Task> getAllTasksByOwnerId(UUID userId) {
        List<Task> tasks = taskRepository.findByOwner_userId(userId);
        return Collections.unmodifiableList(tasks);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }
}
