package com.invokegs.dbcoursework.controller;

import com.invokegs.dbcoursework.dto.UserDto;
import com.invokegs.dbcoursework.entity.User;
import com.invokegs.dbcoursework.exception.RegistrationInvalidDataException;
import com.invokegs.dbcoursework.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller("register")
@RequestMapping("/register")
public class RegisterController {
    private final UserService userService;
    private final PasswordEncoder encoder;

    public RegisterController(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @GetMapping
    public String index(Model model) {
        return "register";
    }

    @PostMapping
    public String newUser(UserDto user, Model model) {
        if (!Objects.equals(user.getRepeatPassword(), user.getPassword())) {
            model.addAttribute("wrong_pass", true);
            return "register";
        }

        try {
            userService.registerUser(new User(
                    user.getUsername(),
                    user.getEmail(),
                    encoder.encode(user.getPassword()),
                    user.getFirstName(),
                    user.getLastName()
            ));
            return "redirect:/login";
        } catch (RegistrationInvalidDataException e) {
            model.addAttribute("email_used", e.isEmailInUse());
            model.addAttribute("username_used", e.isUsernameInUse());
            return "register";
        }
    }
}
