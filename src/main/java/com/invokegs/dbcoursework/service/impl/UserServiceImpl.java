package com.invokegs.dbcoursework.service.impl;

import com.invokegs.dbcoursework.entity.ConfirmToken;
import com.invokegs.dbcoursework.entity.User;
import com.invokegs.dbcoursework.exception.RegistrationInvalidDataException;
import com.invokegs.dbcoursework.repository.ConfirmTokenRepository;
import com.invokegs.dbcoursework.repository.RoleRepository;
import com.invokegs.dbcoursework.repository.UserRepository;
import com.invokegs.dbcoursework.service.EmailSenderService;
import com.invokegs.dbcoursework.service.UserService;
import org.springframework.lang.NonNull;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.UUID;

@Service("userService")
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final ConfirmTokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final EmailSenderService senderService;

    public UserServiceImpl(UserRepository repository, ConfirmTokenRepository tokenRepository,
                           RoleRepository roleRepository, EmailSenderService senderService) {
        this.repository = repository;
        this.tokenRepository = tokenRepository;
        this.roleRepository = roleRepository;
        this.senderService = senderService;
    }

    @Override
    public User findByUsername(@NonNull String username) {
        return repository.findUserByUsername(username);
    }

    @Override
    public User findById(@NonNull Long userId) {
        return repository.findById(userId).orElse(null);
    }

    @Override
    @Transactional
    public void registerUser(@NonNull User user, String confirmUrl) throws RegistrationInvalidDataException {
        final User userDb = repository.findUserByUsernameOrEmail(user.getUsername(), user.getEmail());

        if (userDb != null) {
            throw new RegistrationInvalidDataException(
                    user.getEmail().equals(userDb.getEmail()),
                    user.getUsername().equals(userDb.getUsername())
            );
        }

        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        repository.save(user);

        sendRegistrationConfirmation(user, confirmUrl);
    }

    @Override
    public void sendRegistrationConfirmation(User user, String confirmUrl) {
        final var token = UUID.randomUUID().toString();

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Confirm Registration");
        email.setText("To confirm registration click on link: " + confirmUrl + token);

        senderService.sendEmailAsync(email)
                .thenRun(() -> tokenRepository.save(new ConfirmToken(token, user)));
    }

    @Override
    public boolean confirm(String token) {
        return repository.activateUser(token);
    }
}
