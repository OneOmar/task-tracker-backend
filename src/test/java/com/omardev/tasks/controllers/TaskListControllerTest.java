package com.omardev.tasks.controllers;

import com.omardev.tasks.dto.TaskListDto;
import com.omardev.tasks.entities.TaskList;
import com.omardev.tasks.mappers.TaskListMapper;
import com.omardev.tasks.services.TaskListService;
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
public class TaskListControllerTest {

    @Mock TaskListService service;
    @Mock TaskListMapper mapper;
    @InjectMocks TaskListController controller;

    UUID id;

    @BeforeEach
    void setUp() { id = UUID.randomUUID(); }

    // POST /task-lists : successful create -> 201
    @Test
    void createTaskList_Successful_Creates() {
        // 1️⃣ Prepare request DTO and expected objects
        TaskListDto requestDto = new TaskListDto(null, "New Title", "Desc", 0, null, null);
        TaskList requestEntity = new TaskList(null, "New Title", "Desc", null, null, null);
        TaskList createdEntity = new TaskList(id, "New Title", "Desc", null, null, null);
        TaskListDto createdDto = new TaskListDto(id, "New Title", "Desc", 0, null, null);

        // 2️⃣ Mock mapper/service chain
        when(mapper.fromDto(requestDto)).thenReturn(requestEntity);
        when(service.createTaskList(requestEntity)).thenReturn(createdEntity);
        when(mapper.toDto(createdEntity)).thenReturn(createdDto);

        // 3️⃣ Call controller directly
        ResponseEntity<TaskListDto> resp = controller.createTaskList(requestDto);

        // 4️⃣ Assert response status and body
        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        assertEquals(createdDto, resp.getBody());

        // 5️⃣ Verify interactions
        verify(mapper).fromDto(requestDto);
        verify(service).createTaskList(requestEntity);
        verify(mapper).toDto(createdEntity);
    }

    // GET /task-lists/{id} : not found -> 404
    @Test
    void getTaskListById_NotFound_Returns404() {
        // 1️⃣ Mock service to return empty
        when(service.getTaskList(id)).thenReturn(Optional.empty());

        // 2️⃣ Call controller directly
        ResponseEntity<TaskListDto> resp = controller.getTaskListById(id);

        // 3️⃣ Assert 404 not found
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());

        // 4️⃣ Verify service was called
        verify(service).getTaskList(id);
    }

}
