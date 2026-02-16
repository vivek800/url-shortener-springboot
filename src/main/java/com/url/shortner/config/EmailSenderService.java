package com.url.shortner.config;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.url.shortner.entity.Message;

import lombok.RequiredArgsConstructor;

@Service
 @RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;

    public EmailSenderService(JavaMailSender mailSender)
    {
    	this.mailSender = mailSender;
    }
    
    public void sendEmail(Message message) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(message.getRecipient());
        mail.setSubject("Test Email");
        mail.setText(message.getContent());

        mailSender.send(mail);
    }
    
}

