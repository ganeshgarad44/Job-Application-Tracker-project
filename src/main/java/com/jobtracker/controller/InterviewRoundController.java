package com.jobtracker.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobtracker.dto.ApiResponse;
import com.jobtracker.dto.InterviewRoundRequest;
import com.jobtracker.dto.InterviewRoundResponse;
import com.jobtracker.config.CurrentUserService;
import com.jobtracker.service.InterviewRoundService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/interview-rounds")
public class InterviewRoundController {

    private final InterviewRoundService interviewRoundService;
    private final CurrentUserService currentUserService;

    public InterviewRoundController(InterviewRoundService interviewRoundService, CurrentUserService currentUserService) {
        this.interviewRoundService = interviewRoundService;
        this.currentUserService = currentUserService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InterviewRoundResponse>>> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Interview rounds fetched",
                interviewRoundService.getAllByUser(currentUserService.getCurrentUser().getId())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InterviewRoundResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Interview round fetched",
                interviewRoundService.getById(id, currentUserService.getCurrentUser().getId())));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<InterviewRoundResponse>> create(@RequestParam Long applicationId, @Valid @RequestBody InterviewRoundRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Interview round created",
                interviewRoundService.create(applicationId, currentUserService.getCurrentUser().getId(), request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<InterviewRoundResponse>> update(@PathVariable Long id, @Valid @RequestBody InterviewRoundRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Interview round updated",
                interviewRoundService.update(id, currentUserService.getCurrentUser().getId(), request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        interviewRoundService.delete(id, currentUserService.getCurrentUser().getId());
        return ResponseEntity.ok(new ApiResponse<>(true, "Interview round deleted", null));
    }
}