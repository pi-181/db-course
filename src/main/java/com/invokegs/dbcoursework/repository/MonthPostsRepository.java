package com.invokegs.dbcoursework.repository;

import com.invokegs.dbcoursework.entity.MonthPosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthPostsRepository extends JpaRepository<MonthPosts, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM posts_by_months")
    List<MonthPosts> getMonths();
}
