package com.invokegs.dbcoursework.repository;

import com.invokegs.dbcoursework.entity.PostCount;
import com.invokegs.dbcoursework.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCountRepository extends JpaRepository<PostCount, User> {
    @NonNull
    List<PostCount> findAll();
    @NonNull
    List<PostCount> findTop10ByOrderByPostsDesc();
    @Nullable
    PostCount findByUser(User user);
}
