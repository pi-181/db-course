package com.invokegs.dbcoursework.service.impl;

import com.invokegs.dbcoursework.entity.User;
import com.invokegs.dbcoursework.exception.RegistrationInvalidDataException;
import com.invokegs.dbcoursework.repository.UserRepository;
import com.invokegs.dbcoursework.service.UserService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("userService")
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
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

        user.setEnabled(true); // todo: email verification
        repository.save(user);
    }
}
