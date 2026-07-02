package com.jobtracker.dto;

import java.time.LocalDateTime;

public record ReminderResponse(
        Long id,
        String reminderType,
        LocalDateTime reminderTime,
        Boolean sent,
        String message,
        Long userId,
        Long applicationId,
        LocalDateTime createdAt) {
}