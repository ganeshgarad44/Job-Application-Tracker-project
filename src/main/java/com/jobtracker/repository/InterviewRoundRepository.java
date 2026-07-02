package com.jobtracker.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobtracker.entity.InterviewRound;

public interface InterviewRoundRepository extends JpaRepository<InterviewRound, Long> {
    List<InterviewRound> findByReminderSentFalseAndInterviewDateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<InterviewRound> findByApplicationId(Long applicationId);
    java.util.Optional<InterviewRound> findByIdAndApplicationUserId(Long id, Long userId);
    List<InterviewRound> findByApplicationUserId(Long userId);
}