package com.omardev.tasks.controllers;

import com.omardev.tasks.dto.TaskDto;
import com.omardev.tasks.entities.Task;
import com.omardev.tasks.entities.TaskPriority;
import com.omardev.tasks.entities.TaskStatus;
import com.omardev.tasks.mappers.TaskMapper;
import com.omardev.tasks.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TasksControllerTest {

    @Mock TaskService taskService;
    @Mock TaskMapper taskMapper;
    @InjectMocks TasksController controller;

    UUID listId;
    UUID taskId;

    @BeforeEach
    void setUp() {
        listId = UUID.randomUUID();
        taskId = UUID.randomUUID();
    }

    // POST /task-lists/{taskListId}/tasks : successful create -> 201
    @Test
    void createTask_Successful_Creates() {
        // 1️⃣ Prepare request DTO and expected objects
        TaskDto requestDto = new TaskDto(null, "New Title", "Desc", null, TaskPriority.HIGH, null);
        Task requestEntity = new Task(null, "New Title", "Desc", null, TaskPriority.HIGH, null, null, null, null);
        Task createdEntity = new Task(taskId, "New Title", "Desc", null, TaskPriority.HIGH, TaskStatus.OPEN, null, null, null);
        TaskDto createdDto = new TaskDto(taskId, "New Title", "Desc", null, TaskPriority.HIGH, TaskStatus.OPEN);

        // 2️⃣ Mock mapper & service
        when(taskMapper.fromDto(requestDto)).thenReturn(requestEntity);
        when(taskService.createTask(listId, requestEntity)).thenReturn(createdEntity);
        when(taskMapper.toDto(createdEntity)).thenReturn(createdDto);

        // 3️⃣ Call controller directly
        ResponseEntity<TaskDto> resp = controller.createTask(listId, requestDto);

        // 4️⃣ Assert status and body
        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        assertEquals(createdDto, resp.getBody());

        // 5️⃣ Verify interactions
        verify(taskMapper).fromDto(requestDto);
        verify(taskService).createTask(listId, requestEntity);
        verify(taskMapper).toDto(createdEntity);
    }

    // GET /task-lists/{taskListId}/tasks/{taskId} : not found -> 404
    @Test
    void getTask_NotFound_Returns404() {
        // 1️⃣ Mock service to return empty
        when(taskService.getTask(listId, taskId)).thenReturn(Optional.empty());

        // 2️⃣ Call controller directly
        ResponseEntity<TaskDto> resp = controller.getTask(listId, taskId);

        // 3️⃣ Assert 404
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());

        // 4️⃣ Verify service called
        verify(taskService).getTask(listId, taskId);
    }
}