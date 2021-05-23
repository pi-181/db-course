package com.invokegs.dbcoursework.service.impl;

import com.invokegs.dbcoursework.entity.Post;
import com.invokegs.dbcoursework.repository.PostRepository;
import com.invokegs.dbcoursework.service.PostService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service("postService")
public class PostServiceImpl implements PostService {
    private final PostRepository repository;

    public PostServiceImpl(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public void savePost(@NonNull Post post) {
        repository.save(post);
    }

    @NonNull
    @Override
    public Collection<Post> getPosts() {
        return repository.findAllByOrderByCreationTimeDesc();
    }

    @Override
    public Optional<Post> getPost(Long postId) {
        return repository.findById(postId);
    }

    @Override
    public void deletePost(Long postId) {
        repository.deleteById(postId);
    }
}
