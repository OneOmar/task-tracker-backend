package com.omardev.tasks.services.impl;

import com.omardev.tasks.entities.TaskList;
import com.omardev.tasks.repositories.TaskListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskListServiceImplTest {

    @Mock
    TaskListRepository repo;

    @InjectMocks
    TaskListServiceImpl service;

    // Create: successful save, repo.save called and returns entity
    @Test
    void createTaskList_Successful_Saves() {
        // 1️⃣ Make a TaskList object to give to the service (no ID yet)
        TaskList taskList = new TaskList(null, "Title", "Desc", null, null, null);

        // 2️⃣ Tell the mock repository:
        // "Whenever someone calls save(...), just give back the same object you got."
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0)); // echo saved

        // 3️⃣ Call the service method we want to test
        TaskList saved = service.createTaskList(taskList);

        // 4️⃣ Check that the TaskList returned by the service has the title we set
        assertEquals("Title", saved.getTitle());

        // 5️⃣ Make sure the repo's save method really was called once
        verify(repo).save(any(TaskList.class));
    }


    // Create: cannot create when ID already present
    @Test
    void createTaskList_WithId_Throws() {
        // 1️⃣ Make a TaskList that already has an ID (pretend it came from somewhere else)
        TaskList t = new TaskList(UUID.randomUUID(), "Title", "desc", null, null, null);

        // 2️⃣ Expect the service to throw an exception — creating something with an ID is not allowed
        assertThrows(IllegalArgumentException.class, () -> service.createTaskList(t));
    }


    // Update: successful update — existing found and title changed
    @Test
    void updateTaskList_Successful_Updates() {
        // 1️⃣ Set up IDs and objects
        UUID id = UUID.randomUUID();
        TaskList existing = new TaskList(id, "Old", "desc", null, LocalDateTime.now(), LocalDateTime.now()); // already in DB
        TaskList update = new TaskList(id, "New", "new desc", null, null, null); // changes we want

        // 2️⃣ Mock repo to say: "Yes, I found the existing task list"
        when(repo.findById(id)).thenReturn(Optional.of(existing));

        // 3️⃣ Mock repo save: just return whatever object you give me (echo)
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        // 4️⃣ Call service update method
        TaskList result = service.updateTaskList(id, update);

        // 5️⃣ Verify that the title was updated
        assertEquals("New", result.getTitle());

        // 6️⃣ Ensure save() was called with the same existing object (now updated)
        verify(repo).save(existing);
    }


    // Delete: ensure repository deleteById is invoked
    @Test
    void deleteTaskList_CallsRepo() {
        // 1️⃣ Make a random ID
        UUID id = UUID.randomUUID();

        // 2️⃣ Call service delete method
        service.deleteTaskList(id);

        // 3️⃣ Ensure repo.deleteById was called with that ID
        verify(repo).deleteById(id);
    }
}
