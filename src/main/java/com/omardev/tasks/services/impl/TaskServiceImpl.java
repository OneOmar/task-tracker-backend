package com.omardev.tasks.services.impl;

import com.omardev.tasks.entities.Task;
import com.omardev.tasks.entities.TaskList;
import com.omardev.tasks.entities.TaskPriority;
import com.omardev.tasks.entities.TaskStatus;
import com.omardev.tasks.repositories.TaskListRepository;
import com.omardev.tasks.repositories.TaskRepository;
import com.omardev.tasks.services.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskListRepository taskListRepository;
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskListRepository taskListRepository,
                           TaskRepository taskRepository) {
        this.taskListRepository = taskListRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getAllByTaskListId(UUID taskListId) {
        return taskRepository.findAllByTaskListId(taskListId);
    }
    @Override
    public Task createTask(UUID taskListId, Task task) {
        if (task.getId() != null) {
            throw new IllegalArgumentException("Task already has an ID.");
        }

        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task must have a title.");
        }

        TaskPriority priority = Optional.ofNullable(task.getPriority())
                .orElse(TaskPriority.MEDIUM);

        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Task List ID."));

        LocalDateTime now = LocalDateTime.now();

        Task newTask = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                priority,
                TaskStatus.OPEN,
                taskList,
                now,
                now
        );

        return taskRepository.save(newTask);
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
        return taskRepository.findByTaskListIdAndId(taskListId, taskId);
    }

    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        if (task.getId() == null || !task.getId().equals(taskId)) {
            throw new IllegalArgumentException("Invalid or mismatched Task ID!");
        }
        if (task.getPriority() == null || task.getStatus() == null) {
            throw new IllegalArgumentException("Priority and status are required!");
        }

        Task existing = taskRepository.findByTaskListIdAndId(taskListId, taskId)
                .orElseThrow(() -> new IllegalStateException("Task not found!"));

        existing.setTitle(task.getTitle());
        existing.setDescription(task.getDescription());
        existing.setDueDate(task.getDueDate());
        existing.setPriority(task.getPriority());
        existing.setStatus(task.getStatus());
        existing.setUpdated(LocalDateTime.now());

        return taskRepository.save(existing);
    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
    }
}