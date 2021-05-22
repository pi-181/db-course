package com.invokegs.dbcoursework.controller;

import com.invokegs.dbcoursework.dto.UserDto;
import com.invokegs.dbcoursework.entity.User;
import com.invokegs.dbcoursework.exception.RegistrationInvalidDataException;
import com.invokegs.dbcoursework.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
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
    public String index(UserDto user, Model model) {
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping
    @Transactional
    public String newUser(@Valid @ModelAttribute("user") UserDto user, BindingResult result,
                          Model model, HttpServletRequest request) {
        model.addAttribute("user", user);

        if (!Objects.equals(user.getConfirmPassword(), user.getPassword()))
            result.rejectValue("confirmPassword", "error.user.passwordNotMatch", "пароли не совпадают");

        if (result.hasErrors())
            return "register";

        try {
            final User u = new User(
                    user.getUsername(),
                    user.getEmail(),
                    encoder.encode(user.getPassword()),
                    user.getFirstName(),
                    user.getLastName()
            );
            userService.registerUser(u);

            final String url = request.getRequestURL().toString();
            userService.sendRegistrationConfirmation(u, url + "/confirm/");

            return "redirect:/login";
        } catch (RegistrationInvalidDataException e) {
            model.addAttribute("email_used", e.isEmailInUse());
            model.addAttribute("username_used", e.isUsernameInUse());
            return "register";
        }
    }

    @GetMapping("confirm/{token}")
    @Transactional
    public String confirm(@Valid @PathVariable("token") String token, Model model) {
        final boolean confirm = userService.confirm(token);

        if (!confirm) {
            throw new IllegalArgumentException("Token '" + token + "' doesn't exist!");
        }

        return "redirect:/home";
    }
}
