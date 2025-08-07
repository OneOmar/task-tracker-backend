package com.omardev.tasks.controllers;

import com.omardev.tasks.dto.TaskListDto;
import com.omardev.tasks.entities.TaskList;
import com.omardev.tasks.mappers.TaskListMapper;
import com.omardev.tasks.services.TaskListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task-lists")
@Validated
public class TaskListController {

    private final TaskListService taskListService;
    private final TaskListMapper taskListMapper;

    public TaskListController(TaskListService taskListService, TaskListMapper taskListMapper) {
        this.taskListService = taskListService;
        this.taskListMapper = taskListMapper;
    }

    /**
     * Retrieves all task lists.
     *
     * @return list of task list DTOs
     */
    @GetMapping
    public ResponseEntity<List<TaskListDto>> getAllTaskLists() {
        List<TaskListDto> dtos = taskListService.getAllTaskLists()
                .stream()
                .map(taskListMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    /**
     * Creates a new task list.
     *
     * @param dto the task list DTO
     * @return the created task list DTO
     */
    @PostMapping
    public ResponseEntity<TaskListDto> createTaskList(@RequestBody TaskListDto dto) {
        TaskList taskList = taskListMapper.fromDto(dto);
        TaskList created = taskListService.createTaskList(taskList);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskListMapper.toDto(created));
    }

    /**
     * Retrieves a task list by its ID.
     *
     * @param taskListId the ID of the task list
     * @return the task list DTO
     */
    @GetMapping("/{taskListId}")
    public ResponseEntity<TaskListDto> getTaskListById(@PathVariable UUID taskListId) {
        return taskListService.getTaskList(taskListId)
                .map(taskListMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Updates an existing task list.
     *
     * @param taskListId the ID of the task list to update
     * @param dto the updated task list DTO
     * @return the updated task list DTO
     */
    @PutMapping("/{taskListId}")
    public ResponseEntity<TaskListDto> updateTaskList(
            @PathVariable UUID taskListId,
            @RequestBody TaskListDto dto) {

        TaskList taskList = taskListMapper.fromDto(dto);
        TaskList updated = taskListService.updateTaskList(taskListId, taskList);
        return ResponseEntity.ok(taskListMapper.toDto(updated));
    }

    /**
     * Deletes a task list by its ID.
     *
     * @param taskListId the ID of the task list to delete
     */
    @DeleteMapping("/{taskListId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaskList(@PathVariable UUID taskListId) {
        taskListService.deleteTaskList(taskListId);
    }
}
