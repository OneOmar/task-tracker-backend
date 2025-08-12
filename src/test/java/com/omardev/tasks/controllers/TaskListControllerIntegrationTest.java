package com.omardev.tasks.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omardev.tasks.dto.TaskListDto;
import com.omardev.tasks.entities.TaskList;
import com.omardev.tasks.repositories.TaskListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskListControllerIntegrationTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired TaskListRepository taskListRepository;

    @BeforeEach
    void setUp() {
        taskListRepository.deleteAll(); // clean DB for each test
    }

    // POST /task-lists : successful create -> 201
    @Test
    void createTaskList_Successful_Creates() throws Exception {
        // 1️⃣ Build request DTO (no id)
        TaskListDto request = new TaskListDto(null, "Integration Title", "Desc", 0, null, null);

        // 2️⃣ POST to controller and expect 201 + returned JSON contains id and title
        mockMvc.perform(post("/task-lists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Integration Title"))
                .andExpect(jsonPath("$.id").value(not("null"))); // has id
    }

    // GET /task-lists/{id} : not found -> 404
    @Test
    void getTaskListById_NotFound_Returns404() throws Exception {
        // 1️⃣ Use a random UUID that does not exist
        UUID id = UUID.randomUUID();

        // 2️⃣ GET should return 404
        mockMvc.perform(get("/task-lists/{taskListId}", id))
                .andExpect(status().isNotFound());
    }
}
