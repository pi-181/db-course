package com.invokegs.dbcoursework.service.impl;

import com.invokegs.dbcoursework.entity.ConfirmToken;
import com.invokegs.dbcoursework.entity.User;
import com.invokegs.dbcoursework.exception.RegistrationInvalidDataException;
import com.invokegs.dbcoursework.repository.ConfirmTokenRepository;
import com.invokegs.dbcoursework.repository.UserRepository;
import com.invokegs.dbcoursework.service.EmailSenderService;
import com.invokegs.dbcoursework.service.UserService;
import org.springframework.lang.NonNull;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service("userService")
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final ConfirmTokenRepository tokenRepository;
    private final EmailSenderService senderService;

    public UserServiceImpl(UserRepository repository, ConfirmTokenRepository tokenRepository,
                           EmailSenderService senderService) {
        this.repository = repository;
        this.tokenRepository = tokenRepository;
        this.senderService = senderService;
    }

    @Override
    @Transactional
    public void registerUser(@NonNull User user) throws RegistrationInvalidDataException {
        final User userDb = repository.findUserByUsernameOrEmail(user.getUsername(), user.getEmail());

        if (userDb != null) {
            throw new RegistrationInvalidDataException(
                    user.getEmail().equals(userDb.getEmail()),
                    user.getUsername().equals(userDb.getUsername())
            );
        }

        repository.save(user);
    }

    @Override
    public void sendRegistrationConfirmation(User user, String conformationUrl) {
        final var token = UUID.randomUUID().toString();
        tokenRepository.save(new ConfirmToken(token, user));

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Confirm Registration");
        email.setText("To confirm registration click on link: " + conformationUrl + token);
        senderService.sendEmail(email);
    }

    @Override
    public boolean confirm(String token) {
        return repository.activateUser(token);
    }
}
