package com.jobtracker.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record ApplicationRequest(
        @NotBlank String companyName,
        @NotBlank String roleTitle,
        @NotBlank String status,
        LocalDate appliedDate,
        String jobUrl,
        String notes) {
}