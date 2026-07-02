package com.jobtracker.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InterviewRoundRequest(
        @NotBlank String roundName,
        @NotNull LocalDateTime interviewDateTime,
        @NotBlank String interviewMode,
        @NotBlank String interviewerName,
        String meetingLink,
        String notes) {
}