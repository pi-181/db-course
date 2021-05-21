package com.invokegs.dbcoursework.controller;

import com.invokegs.dbcoursework.entity.SecurityUserDetails;
import com.invokegs.dbcoursework.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("profile")
@RequestMapping("/profile")
public class ProfileController {
    @GetMapping
    public String index(Model model) {
        Object details = SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
        if (!(details instanceof SecurityUserDetails y)) {
            return "redirect:/login";
        }

        return "redirect:/profile/" + y.getUser().getId();
    }

    @GetMapping("{userId}")
    public String userProfile(@PathVariable(value="userId") User user, Model model) {
        model.addAttribute("user", user);
        return "profile";
    }
}
