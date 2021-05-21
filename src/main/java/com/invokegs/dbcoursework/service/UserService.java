package com.invokegs.dbcoursework.service;

import com.invokegs.dbcoursework.entity.User;
import org.springframework.lang.NonNull;

public interface UserService {
    void registerUser(@NonNull User user);
    void sendRegistrationConfirmation(User user, String conformationUrl);
    void confirm(String token);
}
