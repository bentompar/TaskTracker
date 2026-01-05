package com.BenjaminPark.mapper;

import com.BenjaminPark.domain.Task;
import com.BenjaminPark.domain.TaskStatus;
import com.BenjaminPark.domain.User;
import com.BenjaminPark.dto.CreateTaskDTO;
import com.BenjaminPark.dto.TaskResponse;
import com.BenjaminPark.dto.UpdateTaskDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


import java.util.UUID;

@Component
public class TaskMapper {


    /**
     * Creates Task entity from CreateTaskDTO
     * @param createTaskDTO CreateTaskDTO to convert into task entity.
     * @param owner Owner of the task.
     * @return task from CreateTaskDTO.
     */
    public Task fromCreateTaskDTO(CreateTaskDTO createTaskDTO, User owner) {
        Task task =  new Task(owner, createTaskDTO.getTaskName(), createTaskDTO.getTaskDescription());
        task.setDueDate(parseDueDate(createTaskDTO.getDueDate()));
        return task;
    }

    /**
     * Returns updated Task entity from UpdateTaskDTO.
     * Note: If dueDate empty or null in updateTaskDTO, existing due date will be removed.
     * @param task Task to be updated.
     * @param updateTaskDTO UpdateTaskDTO to convert existing task entity.
     * @return updated task from UpdateTaskDTO.
     */
    public Task applyUpdate(Task task, UpdateTaskDTO updateTaskDTO) {
        task.setTaskName(updateTaskDTO.getTaskName());
        task.setTaskDescription(updateTaskDTO.getTaskDescription());
        task.setTaskStatus(TaskStatus.valueOf(updateTaskDTO.getTaskStatus()));
        task.setDueDate(parseDueDate(updateTaskDTO.getDueDate()));
        return task;
    }

    /**
     * Returns TaskResponse from Task entity.
     * @param task Task entity converter into TaskResponse.
     * @return TaskResponse from task.
     */
    public TaskResponse toTaskResponse(Task task) {
        String dueDateString = task.getDueDate().map(LocalDate::toString).orElse(null);
        return new TaskResponse(task.getTaskId().toString(), task.getTaskName(),
                task.getTaskDescription(), task.getTaskStatus().toString(),
                dueDateString);
    }

    /**
     * Returns dueDate as LocalDate if not null or empty
     * @param dueDate dueDate to be converted.
     * @return parsed task if not null.
     */
    private LocalDate parseDueDate(String dueDate) {
        if (dueDate != null && !dueDate.isEmpty()) {
            return LocalDate.parse(dueDate);
        } else return null;
    }
}
