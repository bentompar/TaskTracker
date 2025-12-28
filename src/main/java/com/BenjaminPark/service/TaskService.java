package com.BenjaminPark.service;

import com.BenjaminPark.domain.Task;
import com.BenjaminPark.exceptions.DuplicateTaskException;
import com.BenjaminPark.exceptions.InvalidUserException;
import com.BenjaminPark.exceptions.MissingTaskException;
import com.BenjaminPark.repository.TaskRepository;
import com.BenjaminPark.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TaskService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public TaskService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) throws DuplicateTaskException {
        if (taskRepository.existsById(task.getTaskId())) {
            throw new DuplicateTaskException("Task already exists!");
        }
        return taskRepository.save(task);
    }

    public Task updateTask(UUID userId, Task task) throws MissingTaskException, InvalidUserException {
        Task taskToUpdate = taskRepository.findById(task.getTaskId()).orElseThrow(() ->
                new MissingTaskException("Task with id " + task.getTaskId() + " does not exist"));
        if (!taskToUpdate.getOwner().getUserId().equals(userId)) {
            throw new InvalidUserException("User does not own this task.");
        }
        taskToUpdate.setTaskName(task.getTaskName());
        taskToUpdate.setTaskDescription(task.getTaskDescription());
        taskToUpdate.setTaskStatus(task.getTaskStatus());
        return taskRepository.save(taskToUpdate);
    }

    public Task deleteTask(UUID userId, Task task) throws MissingTaskException, InvalidUserException {
        Task taskToDelete = taskRepository.findById(task.getTaskId()).orElseThrow(() ->
                new MissingTaskException("Task with id " + task.getTaskId() + " does not exist"));
        if (!taskToDelete.getOwner().getUserId().equals(userId)) {
            throw new InvalidUserException("User does not own this task.");
        }
        taskRepository.delete(taskToDelete);
        return taskToDelete;
    }

    public Task getTaskByTaskId(UUID taskId) throws MissingTaskException {
        return taskRepository.findById(taskId).orElseThrow(() ->
                new MissingTaskException("Task with id " + taskId + " does not exist."));
    }

    public List<Task> getAllTasksByOwnerId(UUID userId) {
        List<Task> tasks = Collections.unmodifiableList(taskRepository.findByTaskOwner(userId));
        if (tasks.isEmpty()) {
            return Collections.emptyList();
        } else {
            return tasks;
        }
    }

    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }
}
