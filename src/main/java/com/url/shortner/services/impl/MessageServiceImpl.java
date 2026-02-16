package com.url.shortner.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.url.shortner.dto.BulkRequest;
import com.url.shortner.dto.MessageStatus;
import com.url.shortner.entity.Message;
import com.url.shortner.repository.MessageRepository;
import com.url.shortner.services.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    @Transactional
    public void saveBulkMessages(BulkRequest request) {

        if (request == null || request.getRecipients() == null || request.getRecipients().isEmpty()) {
            throw new IllegalArgumentException("Recipients list cannot be empty");
        }

        LocalDateTime now = LocalDateTime.now();

        List<Message> messages = request.getRecipients()
                .stream()
                .map(email -> {
                    Message msg = new Message();
                    msg.setRecipient(email.trim());
                    msg.setContent(request.getContent());
                    msg.setStatus(MessageStatus.PENDING);   
                    msg.setRetryCount(0);
                    msg.setNextRetryTime(now);
                    msg.setCreatedAt(now);
                    msg.setUpdatedAt(now);
                    return msg;
                })
                .toList();

        messageRepository.saveAll(messages);
    }
}
