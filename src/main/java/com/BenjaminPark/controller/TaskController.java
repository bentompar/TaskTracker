package com.BenjaminPark.controller;

import com.BenjaminPark.domain.Task;
import com.BenjaminPark.dto.CreateTaskDTO;
import com.BenjaminPark.dto.TaskResponse;
import com.BenjaminPark.dto.UpdateTaskDTO;
import com.BenjaminPark.exceptions.MissingTaskException;
import com.BenjaminPark.mapper.TaskMapper;
import com.BenjaminPark.service.TaskService;
import com.BenjaminPark.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller for managing tasks within the system.
 * Provides CRUD endpoints for creating, retrieving, updating, and deleting tasks.
 * Tasks are associated with a specific user via the userId path variable.
 * <p>
 * Note: Validation, authorization, and persistence of updates will be implemented in later iterations.
 */
@RestController
@RequestMapping("/users/{userId}/tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, UserService userService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.userService = userService;
        this.taskMapper = taskMapper;
    }


    /**
     * Creates a new task for the given user.
     *
     * @param newTask DTO containing task details.
     * @param userId ID of the user who owns this task.
     * @return ResponseEntity containing the created task mapped to TaskResponse and HTTP status 201 Created.
     */
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody CreateTaskDTO newTask, @PathVariable String userId) {
        Task task = taskService.createTask(taskMapper.fromCreateTaskDTO(newTask, userService.getUserById(UUID.fromString(userId))));
        TaskResponse taskResponse = taskMapper.toTaskResponse(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskResponse);
    }

    /**
     * Retrieves a task by its taskId.
     *
     * @param taskId UUID string of the task to retrieve.
     * @return ResponseEntity containing the requested task mapped to TaskResponse and HTTP status 200 OK.
     * @throws MissingTaskException if the task with the given ID does not exist.
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable String taskId) throws MissingTaskException {
        Task task = taskService.getTaskByTaskId(UUID.fromString(taskId));
        TaskResponse taskResponse = taskMapper.toTaskResponse(task);
        return ResponseEntity.status(HttpStatus.OK).body(taskResponse);
    }

    /**
     * Updates an existing task with new details.
     *
     * @param updateTask DTO containing updated task fields.
     * @param taskId     UUID string of the task to update.
     * @param userId     UUID string of the owner user.
     * @return ResponseEntity containing the updated task mapped to TaskResponse and HTTP status 200 OK.
     * @throws MissingTaskException if the task with the given ID does not exist.
     */
    @PatchMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(@RequestBody UpdateTaskDTO updateTask,
                                                   @PathVariable String taskId, @PathVariable String userId) throws MissingTaskException {
        Task oldTask = taskService.getTaskByTaskId(UUID.fromString(taskId));
        Task newTask = taskMapper.applyUpdate(oldTask, updateTask);
        TaskResponse taskResponse = taskMapper.toTaskResponse(taskService.updateTask(UUID.fromString(userId), newTask));

        return ResponseEntity.status(HttpStatus.OK).body(taskResponse);
    }

    /**
     * Deletes a task by its ID for the specified user.
     *
     * @param taskId UUID string of the task to delete.
     * @param userId UUID string of the user requesting deletion.
     * @return ResponseEntity containing the deleted task mapped to TaskResponse and HTTP status 200 OK.
     * @throws MissingTaskException if the task with the given ID does not exist.
     */
    @DeleteMapping("/{taskId}")
    public ResponseEntity<TaskResponse> deleteTask(@PathVariable String taskId, @PathVariable String userId) throws MissingTaskException {
        Task deleteTask = taskService.deleteTask(UUID.fromString(userId), taskService.getTaskByTaskId(UUID.fromString(taskId)));
        TaskResponse taskResponse = taskMapper.toTaskResponse(deleteTask);
        return ResponseEntity.status(HttpStatus.OK).body(taskResponse);


    }
}
