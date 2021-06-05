package com.invokegs.dbcoursework.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface EmailSenderService {
    @Async
    CompletableFuture<Void> sendEmailAsync(SimpleMailMessage email);

    void sendEmail(SimpleMailMessage email);
}
