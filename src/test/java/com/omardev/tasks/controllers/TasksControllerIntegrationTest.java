package com.omardev.tasks.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omardev.tasks.dto.TaskDto;
import com.omardev.tasks.entities.Task;
import com.omardev.tasks.entities.TaskList;
import com.omardev.tasks.entities.TaskPriority;
import com.omardev.tasks.entities.TaskStatus;
import com.omardev.tasks.repositories.TaskListRepository;
import com.omardev.tasks.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TasksControllerIntegrationTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired TaskListRepository taskListRepository;
    @Autowired TaskRepository taskRepository;

    UUID listId;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
        taskListRepository.deleteAll();

        // create a task list to attach tasks to
        TaskList tl = new TaskList(null, "List for integration", "desc", null, null, null);
        TaskList saved = taskListRepository.save(tl);
        listId = saved.getId();
    }

    // POST /task-lists/{taskListId}/tasks : successful create -> 201
    @Test
    void createTask_Successful_Creates() throws Exception {
        // 1️⃣ Prepare request DTO (no id)
        TaskDto request = new TaskDto(null, "IT Task", "Desc", null, TaskPriority.HIGH, null);

        // 2️⃣ POST to create a task and expect HTTP 201 with returned id, title and status
        mockMvc.perform(post("/task-lists/{taskListId}/tasks", listId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("IT Task"))
                .andExpect(jsonPath("$.id").value(not("null")))
                .andExpect(jsonPath("$.status").value("OPEN")); // service sets OPEN by default
    }

    // GET /task-lists/{taskListId}/tasks/{taskId} : not found -> 404
    @Test
    void getTask_NotFound_Returns404() throws Exception {
        // 1️⃣ Use a random task id that does not exist for the existing list
        UUID taskId = UUID.randomUUID();

        // 2️⃣ GET should return 404
        mockMvc.perform(get("/task-lists/{taskListId}/tasks/{taskId}", listId, taskId))
                .andExpect(status().isNotFound());
    }
}
