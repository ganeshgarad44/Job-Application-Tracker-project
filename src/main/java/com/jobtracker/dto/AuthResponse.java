package com.jobtracker.dto;

public record AuthResponse(
        String token,
        String tokenType,
        Long userId,
        String email,
        String fullName) {
}