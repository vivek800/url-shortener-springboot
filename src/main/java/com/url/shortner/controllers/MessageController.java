package com.url.shortner.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.url.shortner.dto.BulkRequest;
import com.url.shortner.services.MessageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    
    public MessageController(MessageService messageService)
    {
    	this.messageService = messageService;
    }

    @PostMapping("/bulk")
    public ResponseEntity<String> sendBulk(@RequestBody @Valid BulkRequest request) {
        messageService.saveBulkMessages(request);
        return ResponseEntity.accepted().body("Accepted");
    }
}
