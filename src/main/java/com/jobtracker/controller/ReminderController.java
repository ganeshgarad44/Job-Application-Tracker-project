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
import com.jobtracker.dto.ReminderRequest;
import com.jobtracker.dto.ReminderResponse;
import com.jobtracker.config.CurrentUserService;
import com.jobtracker.service.ReminderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    private final ReminderService reminderService;
    private final CurrentUserService currentUserService;

    public ReminderController(ReminderService reminderService, CurrentUserService currentUserService) {
        this.reminderService = reminderService;
        this.currentUserService = currentUserService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReminderResponse>>> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Reminders fetched",
                reminderService.getAllByUser(currentUserService.getCurrentUser().getId())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReminderResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Reminder fetched",
                reminderService.getById(id, currentUserService.getCurrentUser().getId())));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReminderResponse>> create(@Valid @RequestBody ReminderRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Reminder created",
                reminderService.create(currentUserService.getCurrentUser().getId(), request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ReminderResponse>> update(@PathVariable Long id, @Valid @RequestBody ReminderRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Reminder updated",
                reminderService.update(id, currentUserService.getCurrentUser().getId(), request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        reminderService.delete(id, currentUserService.getCurrentUser().getId());
        return ResponseEntity.ok(new ApiResponse<>(true, "Reminder deleted", null));
    }
}