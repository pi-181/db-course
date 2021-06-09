package com.invokegs.dbcoursework.controller;

import com.invokegs.dbcoursework.entity.Role;
import com.invokegs.dbcoursework.entity.User;
import com.invokegs.dbcoursework.security.SecurityUserDetails;
import com.invokegs.dbcoursework.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;

@Controller("profile")
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(@AuthenticationPrincipal SecurityUserDetails details, Model model) {
        return "redirect:/profile/" + details.getUserId();
    }

    @GetMapping("{userId}")
    public String userProfile(@PathVariable(value = "userId") Long userId, Model model) {
        final User user = userService.findById(userId);
        model.addAttribute("user", user);
        model.addAttribute("role", user.getRoles().stream()
                .max(Comparator.comparingInt(Role::getPriority))
                .map(Role::getDisplayName)
                .orElse("Unknown"));
        return "profile";
    }
}
