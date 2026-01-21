package com.BenjaminPark.service;

import com.BenjaminPark.domain.Task;
import com.BenjaminPark.domain.TaskStatus;
import com.BenjaminPark.domain.User;
import com.BenjaminPark.dto.CreateTaskDTO;
import com.BenjaminPark.dto.UpdateTaskDTO;
import com.BenjaminPark.exceptions.DuplicateTaskException;
import com.BenjaminPark.exceptions.InvalidUserException;
import com.BenjaminPark.exceptions.MissingTaskException;
import com.BenjaminPark.exceptions.MissingUserIdException;
import com.BenjaminPark.repository.TaskRepository;
import com.BenjaminPark.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public Task createTask(UUID userId, CreateTaskDTO createTaskDTO) throws DuplicateTaskException {
        User owner = userRepository.findById(userId).orElseThrow(() ->
                new MissingUserIdException("UserId Not Found."));
        Task task = new Task(owner, createTaskDTO.getTaskName(),
                createTaskDTO.getTaskDescription(), LocalDate.parse(createTaskDTO.getDueDate()));
        return taskRepository.save(task);
    }

    @PreAuthorize("#userId == principal.userId")
    public Task updateTask(UUID userId, UUID taskId, UpdateTaskDTO updateTaskDTO) throws MissingTaskException, InvalidUserException {
        Task taskToUpdate = taskRepository.findById(taskId).orElseThrow(() ->
                new MissingTaskException("Task with id " + taskId + " does not exist"));
        if (!taskToUpdate.getOwner().getUserId().equals(userId)) {
            throw new InvalidUserException("User does not own this task.");
        }
        taskToUpdate.setTaskName(updateTaskDTO.getTaskName());
        taskToUpdate.setTaskDescription(updateTaskDTO.getTaskDescription());
        taskToUpdate.setTaskStatus(TaskStatus.valueOf(updateTaskDTO.getTaskStatus()));
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
        List<Task> tasks = Collections.unmodifiableList(taskRepository.findByOwner_userId(userId));
        if (tasks.isEmpty()) {
            return Collections.emptyList();
        } else {
            return tasks;
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }
}
