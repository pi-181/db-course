package com.invokegs.dbcoursework.service.impl;

import com.invokegs.dbcoursework.entity.Post;
import com.invokegs.dbcoursework.repository.PostRepository;
import com.invokegs.dbcoursework.service.PostService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service("postService")
public class PostServiceImpl implements PostService {
    private final PostRepository repository;

    public PostServiceImpl(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createPost(@NonNull Post post) {
        repository.save(post);
    }

    @NonNull
    @Override
    public Collection<Post> getPosts() {
        return repository.findAll();
    }
}
