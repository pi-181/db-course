package com.invokegs.dbcoursework.controller;

import com.invokegs.dbcoursework.dto.PostDto;
import com.invokegs.dbcoursework.entity.Post;
import com.invokegs.dbcoursework.entity.User;
import com.invokegs.dbcoursework.security.SecurityUserDetails;
import com.invokegs.dbcoursework.service.PostService;
import com.invokegs.dbcoursework.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class PostController {
    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("posts")
    public String index(Model model) {
        model.addAttribute("posts", postService.getPosts(0));
        return "posts";
    }

    @GetMapping("posts/{page}")
    public String index(@PathVariable("page") int page, Model model) {
        model.addAttribute("posts", postService.getPosts(page - 1));
        return "posts";
    }

    @GetMapping("post-create")
    @PreAuthorize("hasAuthority('CREATE_PRIVILEGE')")
    public String create(PostDto dto, Model model) {
        model.addAttribute("postDto", dto);
        return "create-post";
    }

    @PostMapping("post-create")
    @PreAuthorize("hasAuthority('CREATE_PRIVILEGE')")
    public String create(
            @AuthenticationPrincipal SecurityUserDetails details,
            @Valid @ModelAttribute("postDto") PostDto dto, BindingResult result, Model model) {
        model.addAttribute("postDto", dto);
        if (result.hasErrors()) {
            return "create-post";
        }

        final User user = userService.findById(details.getUserId());
        postService.savePost(new Post(dto.getTitle(), dto.getContent(), user));
        return "redirect:/posts/";
    }

    @GetMapping("post/{postId}")
    public String post(@PathVariable(value = "postId") Long postId, Model model) {
        model.addAttribute("id", postId);
        model.addAttribute("post", postService.getPost(postId).orElseThrow());
        return "post";
    }

    @GetMapping("post-edit/{postId}")
    @PreAuthorize("hasAuthority('EDIT_ANY_PRIVILEGE') || (hasAuthority('EDIT_PRIVILEGE') && isPostAuthor(#postId))")
    public String edit(@PathVariable(value = "postId") Long postId, Model model) {
        final Post post = postService.getPost(postId).orElseThrow();
        model.addAttribute("id", postId);
        model.addAttribute("postDto", new PostDto(post));
        return "post-edit";
    }

    @PostMapping("post-edit/{postId}")
    @PreAuthorize("hasAuthority('EDIT_ANY_PRIVILEGE') || (hasAuthority('EDIT_PRIVILEGE') && isPostAuthor(#postId))")
    public String editPost(@PathVariable(value = "postId") Long postId,
                           @Valid @ModelAttribute("postDto") PostDto postDto,
                           BindingResult result, Model model) {
        final Post post = postService.getPost(postId).orElseThrow();

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

    @PostMapping("post-delete/{postId}")
    @PreAuthorize("hasAuthority('DELETE_ANY_PRIVILEGE') || (hasAuthority('DELETE_PRIVILEGE') && isPostAuthor(#postId))")
    public String delete(@PathVariable(value = "postId") Long postId, Model model) {
        postService.deletePost(postService.getPost(postId).orElseThrow());
        return "redirect:/posts/";
    }
}
