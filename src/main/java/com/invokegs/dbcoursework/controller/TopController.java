package com.invokegs.dbcoursework.controller;

import com.invokegs.dbcoursework.entity.PostCount;
import com.invokegs.dbcoursework.repository.PostCountRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("top")
@RequestMapping("/top")
public class TopController {
    private final PostCountRepository repository;

    public TopController(PostCountRepository repository) {
        this.repository = repository;
    }

    @GetMapping("posts")
    public String posts(Model model) {
        final List<PostCount> top = repository.findTop10ByOrderByPostsDesc();
        LoggerFactory.getLogger(TopController.class).info(top.toString());
        model.addAttribute("top", top);
        return "top-posts";
    }
}
