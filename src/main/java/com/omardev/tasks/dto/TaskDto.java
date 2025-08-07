package com.omardev.tasks.dto;

import com.omardev.tasks.entities.TaskPriority;
import com.omardev.tasks.entities.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(UUID id,
                      String title,
                      String description,
                      LocalDateTime dueDate,
                      TaskPriority priority,
                      TaskStatus status
) { }
