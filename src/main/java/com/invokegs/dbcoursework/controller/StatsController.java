package com.invokegs.dbcoursework.controller;

import com.invokegs.dbcoursework.entity.MonthPosts;
import com.invokegs.dbcoursework.entity.PostCount;
import com.invokegs.dbcoursework.service.StatsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("stats")
@RequestMapping("/stats")
public class StatsController {
    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("author-top")
    public String posts(Model model) {
        final List<PostCount> top = statsService.getAuthorsTop();
        model.addAttribute("top", top);
        return "author-top";
    }

    @GetMapping("posts-months")
    public String months(Model model) {
        final List<MonthPosts> monthPosts = statsService.getMonthPosts();
        model.addAttribute("months", monthPosts);
        return "posts-months";
    }
}
