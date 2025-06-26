package com.information.holiday.config;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class LoggerStartupConfig {

    @EventListener(ApplicationStartedEvent.class)
    public void createLogDirectory() {
        String logPath = "./logs";
        String archivedPath = logPath + "/archived";

        File logDir = new File(logPath);
        File archivedDir = new File(archivedPath);

        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        if (!archivedDir.exists()) {
            archivedDir.mkdirs();
        }
    }
}