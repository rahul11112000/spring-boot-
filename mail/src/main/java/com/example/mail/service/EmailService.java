package com.example.mail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.mail.dto.EmailRequest;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        // message.setFrom("your_email@gmail.com"); // Optional, but recommended
        mailSender.send(message);
    }

    public void sendEmailFromRequest(EmailRequest request) {
        sendSimpleEmail(request.getTo(), request.getSubject(), request.getMessage());
    }
}

