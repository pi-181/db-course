package com.invokegs.dbcoursework.dto;

import com.invokegs.dbcoursework.entity.Post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PostDto {
    @NotBlank
    @Size(min = 5, max = 50)
    private String title;

    @NotBlank
    @Size(min = 50, max = 10000)
    private String content;

    public PostDto() {
    }

    public PostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostDto(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
