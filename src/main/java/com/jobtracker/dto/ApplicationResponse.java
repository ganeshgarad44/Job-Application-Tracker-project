package com.jobtracker.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ApplicationResponse(
        Long id,
        String companyName,
        String roleTitle,
        String status,
        LocalDate appliedDate,
        String jobUrl,
        String notes,
        Long userId,
        LocalDateTime createdAt) {
}