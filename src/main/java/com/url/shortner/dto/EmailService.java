package com.url.shortner.dto;

import java.math.BigDecimal;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendTransactionEmail(String to,
                                     String accountNumber,
                                     BigDecimal amount,
                                     TransactionType type,
                                     BigDecimal balanceAfter) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(type + " Alert - ₹" + amount);
        message.setText(
                "Dear Customer,\n\n" +
                "Your account " + accountNumber +
                " has been " + type +
                " with amount ₹" + amount + ".\n" +
                "Available Balance: ₹" + balanceAfter + "\n\n" +
                "If this was not you, contact support immediately.\n\n" +
                "Regards,\nYour Bank"
        );

        mailSender.send(message);
    }
}

