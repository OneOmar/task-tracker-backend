package com.omardev.tasks.dto;

import java.time.LocalDateTime;

/**
 * Standard error response structure for all handled exceptions.
 */
public record ErrorResponse(
        int status,
        String message,
        String details,
        LocalDateTime timestamp
) {
    public ErrorResponse(int status, String message, String details) {
        this(status, message, details, LocalDateTime.now());
    }
}
