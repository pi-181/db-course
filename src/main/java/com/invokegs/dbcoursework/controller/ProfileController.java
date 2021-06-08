package com.invokegs.dbcoursework.controller;

import com.invokegs.dbcoursework.entity.Role;
import com.invokegs.dbcoursework.security.SecurityUserDetails;
import com.invokegs.dbcoursework.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;

@Controller("profile")
@RequestMapping("/profile")
public class ProfileController {
    @GetMapping
    public String index(@AuthenticationPrincipal SecurityUserDetails details, Model model) {
        return "redirect:/profile/" + details.getUser().getId();
    }

    @GetMapping("{userId}")
    public String userProfile(@PathVariable(value = "userId") User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("role", user.getRoles().stream()
                .max(Comparator.comparingInt(Role::getPriority))
                .map(Role::getDisplayName)
                .orElse("Unknown"));
        return "profile";
    }
}
