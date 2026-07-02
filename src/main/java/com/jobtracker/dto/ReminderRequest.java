package com.jobtracker.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReminderRequest(
        @NotBlank String reminderType,
        @NotNull LocalDateTime reminderTime,
        String message,
        Long applicationId) {
}