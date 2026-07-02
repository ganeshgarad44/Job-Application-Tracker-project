package com.jobtracker.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jobtracker.dto.InterviewRoundRequest;
import com.jobtracker.dto.InterviewRoundResponse;
import com.jobtracker.entity.Application;
import com.jobtracker.entity.InterviewRound;
import com.jobtracker.exception.ResourceNotFoundException;
import com.jobtracker.repository.ApplicationRepository;
import com.jobtracker.repository.InterviewRoundRepository;

@Service
public class InterviewRoundService {

    private final InterviewRoundRepository interviewRoundRepository;
    private final ApplicationRepository applicationRepository;

    public InterviewRoundService(InterviewRoundRepository interviewRoundRepository,
            ApplicationRepository applicationRepository) {
        this.interviewRoundRepository = interviewRoundRepository;
        this.applicationRepository = applicationRepository;
    }

    public List<InterviewRoundResponse> getAllByApplication(Long applicationId) {
        return interviewRoundRepository.findByApplicationId(applicationId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<InterviewRoundResponse> getAllByUser(Long userId) {
        return interviewRoundRepository.findByApplicationUserId(userId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public InterviewRoundResponse getById(Long id, Long userId) {
        return toResponse(findOwnedInterviewRound(id, userId));
    }

    public InterviewRoundResponse create(Long applicationId, Long userId, InterviewRoundRequest request) {
        Application application = findApplication(applicationId);
        if (!application.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Application not found");
        }
        InterviewRound interviewRound = new InterviewRound();
        interviewRound.setRoundName(request.roundName());
        interviewRound.setInterviewDateTime(request.interviewDateTime());
        interviewRound.setInterviewMode(request.interviewMode());
        interviewRound.setInterviewerName(request.interviewerName());
        interviewRound.setMeetingLink(request.meetingLink());
        interviewRound.setNotes(request.notes());
        interviewRound.setApplication(application);
        return toResponse(interviewRoundRepository.save(interviewRound));
    }

    public InterviewRoundResponse update(Long id, Long userId, InterviewRoundRequest request) {
        InterviewRound interviewRound = findOwnedInterviewRound(id, userId);
        interviewRound.setRoundName(request.roundName());
        interviewRound.setInterviewDateTime(request.interviewDateTime());
        interviewRound.setInterviewMode(request.interviewMode());
        interviewRound.setInterviewerName(request.interviewerName());
        interviewRound.setMeetingLink(request.meetingLink());
        interviewRound.setNotes(request.notes());
        return toResponse(interviewRoundRepository.save(interviewRound));
    }

    public void delete(Long id, Long userId) {
        interviewRoundRepository.delete(findOwnedInterviewRound(id, userId));
    }

    private Application findApplication(Long id) {
        return applicationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Application not found"));
    }

    private InterviewRound findOwnedInterviewRound(Long id, Long userId) {
        return interviewRoundRepository.findByIdAndApplicationUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Interview round not found"));
    }

    private InterviewRoundResponse toResponse(InterviewRound interviewRound) {
        return new InterviewRoundResponse(
            interviewRound.getId(),
            interviewRound.getRoundName(),
            interviewRound.getInterviewDateTime(),
            interviewRound.getInterviewMode(),
            interviewRound.getInterviewerName(),
            interviewRound.getMeetingLink(),
            interviewRound.getNotes(),
            interviewRound.getApplication() != null ? interviewRound.getApplication().getId() : null,
            interviewRound.getReminderSent());
    }
}