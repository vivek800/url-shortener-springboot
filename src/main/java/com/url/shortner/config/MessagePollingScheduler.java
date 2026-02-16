package com.url.shortner.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MessagePollingScheduler {

    private final WorkerService workerService;

    public MessagePollingScheduler(WorkerService workerService) {
        this.workerService = workerService;
    }

    @Scheduled(fixedDelay = 2000)
    public void poll() {
        workerService.processBatch();
    }
}
