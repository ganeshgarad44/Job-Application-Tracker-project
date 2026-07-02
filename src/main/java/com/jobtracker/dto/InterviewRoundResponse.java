package com.jobtracker.dto;

import java.time.LocalDateTime;

public record InterviewRoundResponse(
        Long id,
        String roundName,
        LocalDateTime interviewDateTime,
        String interviewMode,
        String interviewerName,
        String meetingLink,
        String notes,
        Long applicationId,
        Boolean reminderSent) {
}