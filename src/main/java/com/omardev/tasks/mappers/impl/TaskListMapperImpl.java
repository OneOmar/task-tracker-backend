package com.omardev.tasks.mappers.impl;

import com.omardev.tasks.entities.TaskList;
import com.omardev.tasks.dto.TaskListDto;
import com.omardev.tasks.mappers.TaskListMapper;
import com.omardev.tasks.entities.Task;
import com.omardev.tasks.mappers.TaskMapper;
import com.omardev.tasks.entities.TaskStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapperImpl implements TaskListMapper {

    private final TaskMapper taskMapper;

    public TaskListMapperImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskList fromDto(TaskListDto dto) {
        List<Task> tasks = Optional.ofNullable(dto.tasks())
                .map(list -> list.stream().map(taskMapper::fromDto).toList())
                .orElse(null);

        return new TaskList(dto.id(), dto.title(), dto.description(), tasks, null, null);
    }

    @Override
    public TaskListDto toDto(TaskList taskList) {
        List<Task> tasks = taskList.getTasks();
        int taskCount = tasks != null ? tasks.size() : 0;
        Double progress = calculateProgress(tasks);

        return new TaskListDto(
                taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                taskCount,
                progress,
                Optional.ofNullable(tasks)
                        .map(t -> t.stream()
                                .map(taskMapper::toDto)
                                .toList())
                        .orElse(null)
        );
    }

    private Double calculateProgress(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) return null;
        long closedTaskCount = tasks.stream()
                .filter(task -> TaskStatus.CLOSED == task.getStatus())
                .count();
        return (double) closedTaskCount / tasks.size();
    }
}
