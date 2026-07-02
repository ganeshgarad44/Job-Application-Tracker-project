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
import org.springframework.web.bind.annotation.RestController;

import com.jobtracker.dto.ApiResponse;
import com.jobtracker.dto.ApplicationRequest;
import com.jobtracker.dto.ApplicationResponse;
import com.jobtracker.config.CurrentUserService;
import com.jobtracker.service.ApplicationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final CurrentUserService currentUserService;

    public ApplicationController(ApplicationService applicationService, CurrentUserService currentUserService) {
        this.applicationService = applicationService;
        this.currentUserService = currentUserService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ApplicationResponse>>> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Applications fetched",
                applicationService.getAllByUser(currentUserService.getCurrentUser().getId())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ApplicationResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Application fetched",
                applicationService.getById(id, currentUserService.getCurrentUser().getId())));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ApplicationResponse>> create(@Valid @RequestBody ApplicationRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Application created",
                applicationService.create(currentUserService.getCurrentUser().getId(), request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ApplicationResponse>> update(@PathVariable Long id, @Valid @RequestBody ApplicationRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Application updated",
                applicationService.update(id, currentUserService.getCurrentUser().getId(), request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        applicationService.delete(id, currentUserService.getCurrentUser().getId());
        return ResponseEntity.ok(new ApiResponse<>(true, "Application deleted", null));
    }
}