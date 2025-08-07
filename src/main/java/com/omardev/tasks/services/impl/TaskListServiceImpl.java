package com.omardev.tasks.services.impl;

import com.omardev.tasks.entities.TaskList;
import com.omardev.tasks.repositories.TaskListRepository;
import com.omardev.tasks.services.TaskListService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;

    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<TaskList> getAllTaskLists() {
        return taskListRepository.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if (taskList.getId() != null) {
            throw new IllegalArgumentException("New task list must not have an ID.");
        }

        String title = taskList.getTitle();
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task list title is required.");
        }

        LocalDateTime now = LocalDateTime.now();

        TaskList newTaskList = new TaskList(
                null,
                title,
                taskList.getDescription(),
                null,
                now,
                now
        );

        return taskListRepository.save(newTaskList);
    }

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return taskListRepository.findById(id);
    }


    @Override
    public TaskList updateTaskList(UUID taskListId, TaskList taskList) {
        UUID id = taskList.getId();

        if (id == null) {
            throw new IllegalArgumentException("Task list must have an ID.");
        }

        if (!id.equals(taskListId)) {
            throw new IllegalArgumentException("Changing the task list ID is not allowed.");
        }

        TaskList existingTaskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalStateException("Task list not found."));

        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setUpdated(LocalDateTime.now());

        return taskListRepository.save(existingTaskList);
    }

    @Override
    public void deleteTaskList(UUID taskListId) {
        taskListRepository.deleteById(taskListId);
    }

}
