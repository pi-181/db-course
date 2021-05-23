package com.invokegs.dbcoursework.controller;

import com.invokegs.dbcoursework.dto.PostDto;
import com.invokegs.dbcoursework.entity.Post;
import com.invokegs.dbcoursework.entity.SecurityUserDetails;
import com.invokegs.dbcoursework.entity.User;
import com.invokegs.dbcoursework.service.PostService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        return "posts";
    }

    @GetMapping("create")
    public String create(PostDto dto, Model model) {
        final SecurityUserDetails details = SecurityUserDetails.getSessionUserDetails().orElseThrow();
        if (!details.hasAuthority("CREATE_PRIVILEGE")) {
            model.addAttribute("message",
                    "Not enough permissions to create posts!");
            return "error";
        }

        model.addAttribute("postDto", dto);
        return "create-post";
    }

    @PostMapping("create")
    public String create(@Valid @ModelAttribute("postDto") PostDto dto, BindingResult result, Model model) {
        final SecurityUserDetails details = SecurityUserDetails.getSessionUserDetails().orElseThrow();
        if (!details.hasAuthority("CREATE_PRIVILEGE")) {
            model.addAttribute("message",
                    "Not enough permissions to create posts!");
            return "error";
        }

        model.addAttribute("postDto", dto);
        if (result.hasErrors()) {
            return "create-post";
        }

        postService.savePost(new Post(dto.getTitle(), dto.getContent(), details.getUser()));
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
        final Post post = postService.getPost(postId).orElseThrow();

        final SecurityUserDetails details = SecurityUserDetails.getSessionUserDetails().orElseThrow();
        if (!details.hasAuthority("EDIT_ANY_PRIVILEGE")
                && !(details.hasAuthority("EDIT_PRIVILEGE") && post.isAuthor(details.getUser()))) {
            model.addAttribute("message", "Not enough permissions to edit that post!");
            return "error";
        }

        model.addAttribute("id", postId);
        model.addAttribute("postDto", new PostDto(post));
        return "post-edit";
    }

    @PostMapping("edit/{postId}")
    public String editPost(@PathVariable(value = "postId") Long postId,
                           @Valid @ModelAttribute("postDto") PostDto postDto,
                           BindingResult result, Model model) {
        final Post post = postService.getPost(postId).orElseThrow();

        final SecurityUserDetails details = SecurityUserDetails.getSessionUserDetails().orElseThrow();
        if (!details.hasAuthority("EDIT_ANY_PRIVILEGE")
                && !(details.hasAuthority("EDIT_PRIVILEGE") && post.isAuthor(details.getUser()))) {
            model.addAttribute("message", "Not enough permissions to edit that post!");
            return "error";
        }

        if (result.hasErrors()) {
            model.addAttribute("id", postId);
            model.addAttribute("postDto", postDto);
            return "post-edit";
        }

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        postService.savePost(post);
        return "redirect:/post/" + postId;
    }

    @GetMapping("delete/{postId}")
    public String delete(@PathVariable(value = "postId") Long postId, Model model) {
        final Post post = postService.getPost(postId).orElseThrow();

        final SecurityUserDetails details = SecurityUserDetails.getSessionUserDetails().orElseThrow();
        if (!details.hasAuthority("DELETE_ANY_PRIVILEGE")
                && !(details.hasAuthority("DELETE_PRIVILEGE") && post.isAuthor(details.getUser()))) {
            model.addAttribute("message", "Not enough permissions to delete that post!");
            return "error";
        }

        postService.deletePost(post);
        return "redirect:/post/";
    }
}
