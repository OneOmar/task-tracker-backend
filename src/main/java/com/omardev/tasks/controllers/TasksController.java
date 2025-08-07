package com.omardev.tasks.controllers;

import com.omardev.tasks.dto.TaskDto;
import com.omardev.tasks.entities.Task;
import com.omardev.tasks.mappers.TaskMapper;
import com.omardev.tasks.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task-lists/{taskListId}/tasks")
public class TasksController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TasksController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks(@PathVariable UUID taskListId) {
        List<TaskDto> tasks = taskService.getAllByTaskListId(taskListId)
                .stream()
                .map(taskMapper::toDto)
                .toList();
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@PathVariable UUID taskListId,
                                              @RequestBody TaskDto taskDto) {
        Task task = taskMapper.fromDto(taskDto);
        Task createdTask = taskService.createTask(taskListId, task);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskMapper.toDto(createdTask));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> getTask(@PathVariable UUID taskListId,
                                           @PathVariable UUID taskId) {
        return taskService.getTask(taskListId, taskId)
                .map(taskMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable UUID taskListId,
                                              @PathVariable UUID taskId,
                                              @RequestBody TaskDto taskDto) {
        Task updatedTask = taskService.updateTask(taskListId, taskId, taskMapper.fromDto(taskDto));
        return ResponseEntity.ok(taskMapper.toDto(updatedTask));
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable UUID taskListId,
                           @PathVariable UUID taskId) {
        taskService.deleteTask(taskListId, taskId);
    }
}
