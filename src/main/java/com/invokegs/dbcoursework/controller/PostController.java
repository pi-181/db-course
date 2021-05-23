package com.invokegs.dbcoursework.controller;

import com.invokegs.dbcoursework.dto.PostDto;
import com.invokegs.dbcoursework.entity.Post;
import com.invokegs.dbcoursework.entity.SecurityUserDetails;
import com.invokegs.dbcoursework.service.PostService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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
        return "posts";
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

        Object details = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(details instanceof SecurityUserDetails y)) {
            return "create-post";
        }

        postService.savePost(new Post(dto.getTitle(), dto.getContent(), y.getUser()));
        return "redirect:/post";
    }

    @GetMapping("{postId}")
    public String post(@PathVariable(value = "postId") Long postId, Model model) {
        model.addAttribute("id", postId);
        model.addAttribute("post", postService.getPost(postId).get());
        return "post";
    }

    @GetMapping("edit/{postId}")
    public String edit(@PathVariable(value = "postId") Long postId, Model model) {
        model.addAttribute("id", postId);
        model.addAttribute("postDto", new PostDto(postService.getPost(postId).get()));
        return "post-edit";
    }

    @PostMapping("edit/{postId}")
    public String editPost(@PathVariable(value = "postId") Long postId, @Valid @ModelAttribute("postDto") PostDto postDto,
                           BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("id", postId);
            model.addAttribute("postDto", postDto);
            return "post-edit";
        }

        final Post post = postService.getPost(postId).get();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        postService.savePost(post);
        return "redirect:/post/" + postId;
    }

    @GetMapping("delete/{postId}")
    public String delete(@PathVariable(value = "postId") Long postId) {
        postService.deletePost(postId);
        return "redirect:/post/";
    }
}
