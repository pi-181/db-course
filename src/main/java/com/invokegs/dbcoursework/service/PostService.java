package com.invokegs.dbcoursework.service;

import com.invokegs.dbcoursework.entity.Post;
import org.springframework.lang.NonNull;

import java.util.Collection;

public interface PostService {
    void createPost(@NonNull Post post);

    @NonNull Collection<Post> getPosts();
}
