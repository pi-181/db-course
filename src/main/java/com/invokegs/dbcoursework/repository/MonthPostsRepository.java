package com.invokegs.dbcoursework.repository;

import com.invokegs.dbcoursework.entity.MonthPosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthPostsRepository extends JpaRepository<MonthPosts, String> {
    @NonNull
    List<MonthPosts> findAll();
}
