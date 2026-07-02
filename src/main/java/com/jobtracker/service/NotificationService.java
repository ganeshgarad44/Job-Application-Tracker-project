package com.jobtracker.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.jobtracker.entity.InterviewRound;

@Service
public class NotificationService {

    private final JavaMailSender mailSender;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendInterviewReminder(String to, InterviewRound interviewRound) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Interview Reminder: " + interviewRound.getRoundName());
        message.setText("Upcoming interview for " + interviewRound.getApplication().getCompanyName()
                + " on " + interviewRound.getInterviewDateTime()
                + " with " + interviewRound.getInterviewerName()
                + (interviewRound.getMeetingLink() != null ? "\nMeeting link: " + interviewRound.getMeetingLink() : ""));
        mailSender.send(message);
    }
}