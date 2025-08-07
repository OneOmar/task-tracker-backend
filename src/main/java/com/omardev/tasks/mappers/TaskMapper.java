package com.omardev.tasks.mappers;

import com.omardev.tasks.dto.TaskDto;
import com.omardev.tasks.entities.Task;

public interface TaskMapper {
    Task fromDto(TaskDto dto);

    TaskDto toDto(Task task);
}
