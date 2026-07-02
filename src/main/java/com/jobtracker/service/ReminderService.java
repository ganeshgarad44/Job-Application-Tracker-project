package com.jobtracker.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jobtracker.dto.ReminderRequest;
import com.jobtracker.dto.ReminderResponse;
import com.jobtracker.entity.Application;
import com.jobtracker.entity.Reminder;
import com.jobtracker.entity.User;
import com.jobtracker.exception.ResourceNotFoundException;
import com.jobtracker.repository.ApplicationRepository;
import com.jobtracker.repository.ReminderRepository;
import com.jobtracker.repository.UserRepository;

@Service
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    public ReminderService(ReminderRepository reminderRepository, UserRepository userRepository,
            ApplicationRepository applicationRepository) {
        this.reminderRepository = reminderRepository;
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
    }

    public List<ReminderResponse> getAllByUser(Long userId) {
        return reminderRepository.findByUserId(userId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public ReminderResponse getById(Long id, Long userId) {
        return toResponse(findOwnedReminder(id, userId));
    }

    public ReminderResponse create(Long userId, ReminderRequest request) {
        User user = findUser(userId);
        Application application = request.applicationId() == null ? null : findApplication(request.applicationId());
        Reminder reminder = new Reminder();
        reminder.setReminderType(request.reminderType());
        reminder.setReminderTime(request.reminderTime());
        reminder.setMessage(request.message());
        reminder.setSent(false);
        reminder.setUser(user);
        reminder.setApplication(application);
        return toResponse(reminderRepository.save(reminder));
    }

    public ReminderResponse update(Long id, Long userId, ReminderRequest request) {
        Reminder reminder = findOwnedReminder(id, userId);
        reminder.setReminderType(request.reminderType());
        reminder.setReminderTime(request.reminderTime());
        reminder.setMessage(request.message());
        if (request.applicationId() != null) {
            reminder.setApplication(findApplication(request.applicationId()));
        }
        return toResponse(reminderRepository.save(reminder));
    }

    public void delete(Long id, Long userId) {
        reminderRepository.delete(findOwnedReminder(id, userId));
    }

    private Reminder findOwnedReminder(Long id, Long userId) {
        return reminderRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Reminder not found"));
    }

    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private Application findApplication(Long id) {
        return applicationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Application not found"));
    }

    private ReminderResponse toResponse(Reminder reminder) {
        return new ReminderResponse(
            reminder.getId(),
            reminder.getReminderType(),
            reminder.getReminderTime(),
            reminder.getSent(),
            reminder.getMessage(),
            reminder.getUser() != null ? reminder.getUser().getId() : null,
            reminder.getApplication() != null ? reminder.getApplication().getId() : null,
            reminder.getCreatedAt());
    }
}