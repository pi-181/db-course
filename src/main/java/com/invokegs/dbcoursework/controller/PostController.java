package com.invokegs.dbcoursework.controller;

import com.invokegs.dbcoursework.dto.PostDto;
import com.invokegs.dbcoursework.entity.Post;
import com.invokegs.dbcoursework.entity.SecurityUserDetails;
import com.invokegs.dbcoursework.service.PostService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("posts", postService.getPosts());
        return "post";
    }

    @GetMapping("create")
    public String create(PostDto dto, Model model) {
        model.addAttribute("postDto", dto);
        return "create-post";
    }

    @PostMapping("create")
    public String create(@Valid @ModelAttribute("postDto") PostDto dto, BindingResult result, Model model) {
        model.addAttribute("postDto", dto);

        if (result.hasErrors()) {
            return "create-post";
        }

        Object details = SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
        if (!(details instanceof SecurityUserDetails y)) {
            return "create-post";
        }

        postService.createPost(new Post(dto.getTitle(), dto.getContent(), y.getUser()));
        return "redirect:/post";
    }
}
