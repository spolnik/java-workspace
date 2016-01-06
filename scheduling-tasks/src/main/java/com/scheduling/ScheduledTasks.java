package com.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class ScheduledTasks {

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        LocalTime now = LocalTime.now();
        System.out.println("The time is now " + DateTimeFormatter.ISO_LOCAL_TIME.format(now));
    }
}
