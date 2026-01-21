package com.BenjaminPark.controller;

import com.BenjaminPark.domain.Task;
import com.BenjaminPark.dto.CreateTaskDTO;
import com.BenjaminPark.dto.TaskResponse;
import com.BenjaminPark.dto.UpdateTaskDTO;
import com.BenjaminPark.exceptions.MissingTaskException;
import com.BenjaminPark.mapper.TaskMapper;
import com.BenjaminPark.security.CustomUserDetails;
import com.BenjaminPark.service.TaskService;
import com.BenjaminPark.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequestMapping("/users/me/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }


    /**
     * Creates a new task for the given user.
     *
     * @param createTaskDTO DTO containing task details.
     * @param customUserDetails custom user details of the user creating the task.
     * @return ResponseEntity containing the created task mapped to TaskResponse and HTTP status 201 Created.
     */
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody CreateTaskDTO createTaskDTO) {
        Task task = taskService.createTask(customUserDetails.getUserId(), createTaskDTO);
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
    public ResponseEntity<TaskResponse> getTask(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable String taskId) throws MissingTaskException {
        Task task = taskService.getTaskByTaskId(customUserDetails.getUserId(), UUID.fromString(taskId));
        TaskResponse taskResponse = taskMapper.toTaskResponse(task);
        return ResponseEntity.status(HttpStatus.OK).body(taskResponse);
    }

    /**
     * Updates an existing task with new details.
     *
     * @param customUserDetails custom user details of the user updating task he owns.
     * @param updateTaskDTO DTO containing updated task fields.
     * @param taskId     UUID string of the task to update.
     * @return ResponseEntity containing the updated task mapped to TaskResponse and HTTP status 200 OK.
     * @throws MissingTaskException if the task with the given ID does not exist.
     */
    @PatchMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody UpdateTaskDTO updateTaskDTO,
            @PathVariable String taskId) throws MissingTaskException {
        Task updateTask = taskService.updateTask(customUserDetails.getUserId(), UUID.fromString(taskId), updateTaskDTO);

        TaskResponse taskResponse = taskMapper.toTaskResponse(updateTask);

        return ResponseEntity.status(HttpStatus.OK).body(taskResponse);
    }

    /**
     * Deletes a task by its ID for the specified user.
     *
     * @param customUserDetails custom user details of the user deleting task he owns.
     * @param taskId UUID string of the task to delete.
     * @return ResponseEntity containing the deleted task mapped to TaskResponse and HTTP status 200 OK.
     * @throws MissingTaskException if the task with the given ID does not exist.
     */
    @DeleteMapping("/{taskId}")
    public ResponseEntity<TaskResponse> deleteTask(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable String taskId) throws MissingTaskException {
        Task deleteTask = taskService.deleteTask(customUserDetails.getUserId(), UUID.fromString(taskId));
        TaskResponse taskResponse = taskMapper.toTaskResponse(deleteTask);
        return ResponseEntity.status(HttpStatus.OK).body(taskResponse);
    }
}
