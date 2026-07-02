package com.jobtracker.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import com.jobtracker.config.AppSchedulerProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jobtracker.entity.InterviewRound;
import com.jobtracker.entity.Reminder;
import com.jobtracker.repository.InterviewRoundRepository;
import com.jobtracker.repository.ReminderRepository;
import com.jobtracker.service.NotificationService;

@Component
public class ReminderScheduler {

    private final InterviewRoundRepository interviewRoundRepository;
    private final ReminderRepository reminderRepository;
    private final NotificationService notificationService;
    private final AppSchedulerProperties appSchedulerProperties;

    public ReminderScheduler(InterviewRoundRepository interviewRoundRepository, ReminderRepository reminderRepository,
            NotificationService notificationService, AppSchedulerProperties appSchedulerProperties) {
        this.interviewRoundRepository = interviewRoundRepository;
        this.reminderRepository = reminderRepository;
        this.notificationService = notificationService;
        this.appSchedulerProperties = appSchedulerProperties;
    }

    @Scheduled(cron = "0 */15 * * * *")
    @Transactional
    public void sendUpcomingInterviewEmails() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lookahead = now.plusHours(appSchedulerProperties.reminderLookaheadHours());
        List<InterviewRound> upcoming = interviewRoundRepository.findByReminderSentFalseAndInterviewDateTimeBetween(now, lookahead);
        for (InterviewRound interviewRound : upcoming) {
            String email = interviewRound.getApplication().getUser().getEmail();
            notificationService.sendInterviewReminder(email, interviewRound);
            interviewRound.setReminderSent(true);
            interviewRoundRepository.save(interviewRound);

            Reminder reminder = new Reminder();
            reminder.setReminderType("INTERVIEW");
            reminder.setReminderTime(interviewRound.getInterviewDateTime().minusMinutes(appSchedulerProperties.reminderWindowMinutes()));
            reminder.setSent(true);
            reminder.setMessage("Email reminder sent for interview round " + interviewRound.getRoundName());
            reminder.setUser(interviewRound.getApplication().getUser());
            reminder.setApplication(interviewRound.getApplication());
            reminderRepository.save(reminder);
        }
    }
}