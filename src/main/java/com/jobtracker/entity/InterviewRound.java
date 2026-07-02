package com.jobtracker.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "interview_rounds")
public class InterviewRound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roundName;

    @Column(nullable = false)
    private LocalDateTime interviewDateTime;

    @Column(nullable = false)
    private String interviewMode;

    @Column(nullable = false)
    private String interviewerName;

    private String meetingLink;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    @JsonIgnore
    private Application application;

    @Column(nullable = false)
    private Boolean reminderSent = false;

    public InterviewRound() {
    }

    public InterviewRound(Long id, String roundName, LocalDateTime interviewDateTime, String interviewMode,
            String interviewerName, String meetingLink, String notes, Application application, Boolean reminderSent) {
        this.id = id;
        this.roundName = roundName;
        this.interviewDateTime = interviewDateTime;
        this.interviewMode = interviewMode;
        this.interviewerName = interviewerName;
        this.meetingLink = meetingLink;
        this.notes = notes;
        this.application = application;
        this.reminderSent = reminderSent;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRoundName() { return roundName; }
    public void setRoundName(String roundName) { this.roundName = roundName; }
    public LocalDateTime getInterviewDateTime() { return interviewDateTime; }
    public void setInterviewDateTime(LocalDateTime interviewDateTime) { this.interviewDateTime = interviewDateTime; }
    public String getInterviewMode() { return interviewMode; }
    public void setInterviewMode(String interviewMode) { this.interviewMode = interviewMode; }
    public String getInterviewerName() { return interviewerName; }
    public void setInterviewerName(String interviewerName) { this.interviewerName = interviewerName; }
    public String getMeetingLink() { return meetingLink; }
    public void setMeetingLink(String meetingLink) { this.meetingLink = meetingLink; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public Application getApplication() { return application; }
    public void setApplication(Application application) { this.application = application; }
    public Boolean getReminderSent() { return reminderSent; }
    public void setReminderSent(Boolean reminderSent) { this.reminderSent = reminderSent; }
}