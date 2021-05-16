package com.invokegs.dbcoursework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("home")
@RequestMapping("/home")
public class HomeController {
    @GetMapping
    public String index(Model model) {
        return "home";
    }
}
