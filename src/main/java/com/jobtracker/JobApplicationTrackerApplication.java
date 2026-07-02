package com.jobtracker;

import com.jobtracker.config.AppSchedulerProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(AppSchedulerProperties.class)
public class JobApplicationTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobApplicationTrackerApplication.class, args);
    }
}