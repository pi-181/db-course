package com.invokegs.dbcoursework.repository;

import com.invokegs.dbcoursework.entity.Post;
import com.invokegs.dbcoursework.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    @NonNull
    Collection<Post> findAllByTitle(String title);

    @NonNull
    Collection<Post> findAllByAuthor(User author);

    @NonNull
    Collection<Post> findAllByOrderByCreationTimeDesc();

    @NonNull
    @Override
    Collection<Post> findAll();
}
