package com.omardev.tasks.mappers;

import com.omardev.tasks.dto.TaskListDto;
import com.omardev.tasks.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto dto);

    TaskListDto toDto(TaskList taskList);
}
