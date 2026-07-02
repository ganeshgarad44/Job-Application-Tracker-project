package com.jobtracker.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jobtracker.dto.ApplicationRequest;
import com.jobtracker.dto.ApplicationResponse;
import com.jobtracker.entity.Application;
import com.jobtracker.entity.User;
import com.jobtracker.exception.ResourceNotFoundException;
import com.jobtracker.repository.ApplicationRepository;
import com.jobtracker.repository.UserRepository;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    public ApplicationService(ApplicationRepository applicationRepository, UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
    }

    public List<ApplicationResponse> getAllByUser(Long userId) {
        return applicationRepository.findByUserId(userId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public ApplicationResponse getById(Long id, Long userId) {
        return toResponse(findOwnedApplication(id, userId));
    }

    public ApplicationResponse create(Long userId, ApplicationRequest request) {
        User user = findUser(userId);
        Application application = new Application();
        application.setCompanyName(request.companyName());
        application.setRoleTitle(request.roleTitle());
        application.setStatus(request.status());
        application.setAppliedDate(request.appliedDate());
        application.setJobUrl(request.jobUrl());
        application.setNotes(request.notes());
        application.setUser(user);
        return toResponse(applicationRepository.save(application));
    }

    public ApplicationResponse update(Long id, Long userId, ApplicationRequest request) {
        Application application = findOwnedApplication(id, userId);
        application.setCompanyName(request.companyName());
        application.setRoleTitle(request.roleTitle());
        application.setStatus(request.status());
        application.setAppliedDate(request.appliedDate());
        application.setJobUrl(request.jobUrl());
        application.setNotes(request.notes());
        return toResponse(applicationRepository.save(application));
    }

    public void delete(Long id, Long userId) {
        applicationRepository.delete(findOwnedApplication(id, userId));
    }

    private Application findOwnedApplication(Long id, Long userId) {
        return applicationRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
    }

    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private ApplicationResponse toResponse(Application application) {
        return new ApplicationResponse(
            application.getId(),
            application.getCompanyName(),
            application.getRoleTitle(),
            application.getStatus(),
            application.getAppliedDate(),
            application.getJobUrl(),
            application.getNotes(),
            application.getUser() != null ? application.getUser().getId() : null,
            application.getCreatedAt());
    }
}