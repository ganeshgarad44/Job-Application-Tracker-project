package com.jobtracker.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.scheduler")
public record AppSchedulerProperties(long reminderWindowMinutes, long reminderLookaheadHours) {
}