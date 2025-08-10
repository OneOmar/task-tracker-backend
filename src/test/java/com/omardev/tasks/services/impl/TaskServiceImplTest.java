package com.omardev.tasks.services.impl;

import com.omardev.tasks.entities.*;
import com.omardev.tasks.repositories.TaskListRepository;
import com.omardev.tasks.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    TaskListRepository listRepo; // Mocked TaskList repository

    @Mock
    TaskRepository taskRepo; // Mocked Task repository

    @InjectMocks
    TaskServiceImpl service; // Service under test

    // Common test IDs
    UUID listId = UUID.randomUUID();
    UUID taskId = UUID.randomUUID();

    // Create: successful save when task list exists
    @Test
    void createTask_Successful_Saves() {
        // 1️⃣ Prepare a TaskList that exists in the repository
        TaskList taskList = new TaskList(listId, "List", "Desc", null, null, null);

        // 2️⃣ Prepare a Task to be created (no ID yet)
        Task input = new Task(null, "Task", "Desc", null, TaskPriority.HIGH, null, null, null, null);

        // 3️⃣ Mock listRepo to say: "Yes, the task list exists"
        when(listRepo.findById(listId)).thenReturn(Optional.of(taskList));

        // 4️⃣ Mock taskRepo save: return exactly what you receive (echo)
        when(taskRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        // 5️⃣ Call the service method
        Task saved = service.createTask(listId, input);

        // 6️⃣ Check that the saved task kept its title
        assertEquals("Task", saved.getTitle());

        // 7️⃣ Verify save() was called on the repo
        verify(taskRepo).save(any(Task.class));
    }

    // Create: cannot create when task already has an ID
    @Test
    void createTask_WithId_Throws() {
        // 1️⃣ Create a Task that already has an ID
        Task task = new Task(UUID.randomUUID(), "Title", "Desc", null, TaskPriority.LOW, TaskStatus.OPEN, null, null, null);

        // 2️⃣ Expect IllegalArgumentException because new tasks must not have an ID
        assertThrows(IllegalArgumentException.class, () -> service.createTask(listId, task));
    }

    // Update: successful update updates fields and persists
    @Test
    void updateTask_Successful_Updates() {
        // 1️⃣ Existing task in repository
        Task existing = new Task(taskId, "Old", "", null, TaskPriority.LOW, TaskStatus.OPEN, null, null, null);

        // 2️⃣ Task object with new data
        Task update = new Task(taskId, "New", "", null, TaskPriority.HIGH, TaskStatus.CLOSED, null, null, null);

        // 3️⃣ Mock findByTaskListIdAndId: return the existing task
        when(taskRepo.findByTaskListIdAndId(listId, taskId)).thenReturn(Optional.of(existing));

        // 4️⃣ Mock save to echo the object passed
        when(taskRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        // 5️⃣ Call service method to update task
        Task result = service.updateTask(listId, taskId, update);

        // 6️⃣ Verify title and status are updated
        assertEquals("New", result.getTitle());
        assertEquals(TaskStatus.CLOSED, result.getStatus());

        // 7️⃣ Verify save was called with the updated existing object
        verify(taskRepo).save(existing);
    }

    // Delete: ensure repository delete method is invoked with IDs
    @Test
    void deleteTask_CallsRepo() {
        // 1️⃣ Call delete method on the service
        service.deleteTask(listId, taskId);

        // 2️⃣ Verify the repo delete method was called with correct IDs
        verify(taskRepo).deleteByTaskListIdAndId(listId, taskId);
    }
}
