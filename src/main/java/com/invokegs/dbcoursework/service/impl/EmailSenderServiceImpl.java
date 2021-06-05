package com.invokegs.dbcoursework.service.impl;

import com.invokegs.dbcoursework.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service("emailSenderService")
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public CompletableFuture<Void> sendEmailAsync(SimpleMailMessage email) {
        javaMailSender.send(email);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }
}
