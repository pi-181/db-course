package com.invokegs.dbcoursework.service;

import com.invokegs.dbcoursework.entity.Post;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.Optional;

public interface PostService {
    void savePost(@NonNull Post post);

    @NonNull Collection<Post> getPosts();

    Optional<Post> getPost(Long postId);

    void deletePost(Long postId);

    void deletePost(Post post);
}
