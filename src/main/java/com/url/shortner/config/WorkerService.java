package com.url.shortner.config;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.url.shortner.dto.MessageStatus;
import com.url.shortner.entity.Message;
import com.url.shortner.repository.MessageRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

 
@Service
@RequiredArgsConstructor
public class WorkerService {

    private static final int BATCH_SIZE = 100;
    private static final int MAX_RETRY = 3;

    private final MessageRepository repository;
    private final EmailSenderService emailSender;
    private final RateLimiterService rateLimiter;
    private final ExecutorService executor;

    public WorkerService(MessageRepository repository , 
    		EmailSenderService emailSender ,ExecutorService executor,
    		RateLimiterService rateLimiter) {
    	this.emailSender =emailSender;
    	this.executor = executor;
    	this.repository = repository;
    	this.rateLimiter = rateLimiter;
    }
    
    public void processBatch() {

        List<Long> messageIds = fetchAndMarkProcessing(BATCH_SIZE);

        for (Long id : messageIds) {
            CompletableFuture.runAsync(() -> processMessage(id), executor);
        }
    }

    
    @Transactional
    public List<Long> fetchAndMarkProcessing(int batchSize) {

        Pageable pageable = PageRequest.of(0, batchSize);
        List<Message> messages = repository.fetchPendingMessages(pageable);

        messages.forEach(m -> m.setStatus(MessageStatus.PROCESSING));

        repository.saveAll(messages);    

        return messages.stream()
                .map(Message::getId)
                .toList();
    }


     
    public void processMessage(Long messageId) {

        Message message = repository.findById(messageId)
                .orElseThrow();

        try {

            if (!rateLimiter.tryAcquire()) {
                message.setStatus(MessageStatus.PENDING);
                message.setNextRetryTime(LocalDateTime.now().plusSeconds(1));
                repository.save(message);
                return;
            }

            emailSender.sendEmail(message);

            message.setStatus(MessageStatus.SENT);
            repository.save(message);

        } catch (Exception ex) {

            handleRetry(message);
            repository.save(message);
        }
    }

    private void handleRetry(Message message) {

        int retry = message.getRetryCount() + 1;
        message.setRetryCount(retry);

        if (retry >= MAX_RETRY) {
            message.setStatus(MessageStatus.FAILED);
            return;
        }

        message.setStatus(MessageStatus.PENDING);

        long backoffSeconds = (long) Math.pow(2, retry);

        message.setNextRetryTime(
                LocalDateTime.now().plusSeconds(backoffSeconds)
        );
    }
}
