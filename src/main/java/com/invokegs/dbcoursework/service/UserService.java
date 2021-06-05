package com.invokegs.dbcoursework.service;

import com.invokegs.dbcoursework.entity.User;
import org.springframework.lang.NonNull;

public interface UserService {
    void registerUser(@NonNull User user, String confirmUrl);
    void sendRegistrationConfirmation(User user, String confirmUrl);
    boolean confirm(String token);
}
